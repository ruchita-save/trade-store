package com.db.tradestore.controller;

import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class TradeController {

    @Autowired
    TradeRepository tradeRepository;

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
    public ResponseEntity<Trade> addTrade(@RequestBody Trade trade){
        try{
            Trade _trade = tradeRepository.save(new Trade(trade.getTradeId(),trade.getVersion(),trade.getCounterPartyId(),
                    trade.getBookId(),trade.getMaturityDate(), new Date()));
            if(_trade.getId() > 0){
                log.info("Trade added successfully with id {}",_trade.getId());
            }
            return new ResponseEntity<>(_trade,HttpStatus.CREATED);
        }catch(Exception e){
            log.error("Exception occurred while adding trade ID {} in trade store.",trade.getTradeId(),e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
