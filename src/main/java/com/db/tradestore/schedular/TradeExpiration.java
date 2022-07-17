package com.db.tradestore.schedular;

import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TradeExpiration {

    private final TradeRepository tradeRepository;

    public TradeExpiration(TradeRepository tradeRepository){
        this.tradeRepository = tradeRepository;
    }

    @Scheduled(cron = "0 0 0 * * *",zone = "Indian/Maldives")
    public List<Trade> expireMaturedTrade(){
        List<Trade> maturedTradeList = tradeRepository.findByMaturityDateLessThanEqual(new Date());
        if(ObjectUtils.isEmpty(maturedTradeList)){
            return null;
        }
        log.info("{} trades expired on {}",maturedTradeList.size(),new Date());
        for(Trade maturedTrade : maturedTradeList){
            maturedTrade.setExpired('Y');
        }
        return tradeRepository.saveAll(maturedTradeList);
    }
}
