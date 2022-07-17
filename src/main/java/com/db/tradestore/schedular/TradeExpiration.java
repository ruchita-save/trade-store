package com.db.tradestore.schedular;

import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TradeExpiration {

    @Autowired
    TradeRepository tradeRepository;

    @Scheduled(cron = "0 0 0 * * *",zone = "Indian/Maldives")
    public void expireMaturedTrade(){
        List<Trade> maturedTradeList = tradeRepository.findByMaturityDateLessThanEqual(new Date());
        log.info("{} trades expired on {}",maturedTradeList.size(),new Date());
        for(Trade maturedTrade : maturedTradeList){
            maturedTrade.setExpired('Y');
        }
        tradeRepository.saveAll(maturedTradeList);
    }
}
