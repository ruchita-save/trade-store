package com.db.tradestore.service;

import com.db.tradestore.model.Trade;

import java.util.Date;

public interface TradeService {

    boolean isLatestVersion(Trade trade);
}
