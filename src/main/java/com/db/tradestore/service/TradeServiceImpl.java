package com.db.tradestore.service;

import com.db.tradestore.model.Trade;

public class TradeServiceImpl implements TradeService{

    @Override
    public boolean isLatestVersion(Trade trade) {
        return true;
    }
}
