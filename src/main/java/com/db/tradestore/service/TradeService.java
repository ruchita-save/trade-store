package com.db.tradestore.service;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public boolean isLatestVersion(TradeDTO trade) {

        log.info("Trade Repository {}" , tradeRepository);

        List<Trade> latestVersionTrades = tradeRepository.findByTradeIdContainingAndVersionGreaterThan(trade.getTradeId(),trade.getVersion());
        log.info("{} Trades found with higher version than current trade {}.",latestVersionTrades.size(),trade.toString());
        return latestVersionTrades.isEmpty();
    }
}
