package com.db.tradestore.controller;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import com.db.tradestore.schedular.TradeExpiration;
import com.db.tradestore.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/api")
public class TradeController {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    TradeService tradeService;

    @GetMapping("/trades/{tradeId}")
    public ResponseEntity<List<Trade>> getTradeByTradeId(@PathVariable(required = true) String tradeId){
        try {
            List<Trade> trades = new ArrayList<Trade>();
            if(tradeId == null || tradeId.isEmpty()){
                log.info("Trade ID not provided in the request.");
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            if (tradeId != null) {
                tradeRepository.findByTradeIdContaining(tradeId).forEach(trades::add);
            }
            if (trades.isEmpty()) {
                log.info("Trade {} not found in store.",tradeId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("{} Trades found for tradeId {}",trades.size(),tradeId);
            return new ResponseEntity<>(trades, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occurred while searching for trade ID {}.",tradeId,e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trades")
    public ResponseEntity<TradeDTO> addTrade(@RequestBody TradeDTO tradeDto){
        try{
            log.info("Trade Service {}" ,tradeService);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<TradeDTO>> constraintViolations = validator.validate(tradeDto);

            TradeDTO responseTradeDTO = new TradeDTO();
            for(ConstraintViolation<?> error : constraintViolations ){
                 responseTradeDTO.setErrorMessage(responseTradeDTO.getErrorMessage().concat(error.getMessage()).concat(". "));
            }

            if(!tradeService.isLatestVersion(tradeDto)){
                responseTradeDTO.setErrorMessage(responseTradeDTO.getErrorMessage().concat(" Latest version is not valid for trade."));
            }
            if(!constraintViolations.isEmpty() || !responseTradeDTO.getErrorMessage().isEmpty()){
                return new ResponseEntity<>(responseTradeDTO,HttpStatus.BAD_REQUEST);
            }
            Trade _trade = tradeRepository.save(new Trade(tradeDto.getTradeId(),tradeDto.getVersion(),tradeDto.getCounterPartyId(),
                    tradeDto.getBookId(),tradeDto.getMaturityDate(), new Date()));

            if(_trade.getId() > 0){
                log.info("Trade added successfully with id {}",_trade.getId());
            }
            responseTradeDTO.setTradeId(_trade.getTradeId());
            responseTradeDTO.setBookId(_trade.getBookId());
            responseTradeDTO.setCounterPartyId(_trade.getCounterPartyId());
            responseTradeDTO.setMaturityDate(_trade.getMaturityDate());
            responseTradeDTO.setVersion(_trade.getVersion());

            responseTradeDTO.setExpired(_trade.getExpired());
            responseTradeDTO.setCreatedDate(_trade.getCreatedDate());
            return new ResponseEntity<>(responseTradeDTO,HttpStatus.CREATED);
        }catch(Exception e){
            log.error("Exception occurred while adding trade ID {} in trade store.",tradeDto.getTradeId(),e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expire")
    public ResponseEntity<List<Trade>> expiryTrade(){
        try{

            TradeExpiration tradeExpiration = new TradeExpiration(tradeRepository);
            tradeExpiration.expireMaturedTrade();

            return new ResponseEntity<>(null,HttpStatus.OK);

        }catch(Exception e){
            log.error("Error occurred while expiration" , e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
