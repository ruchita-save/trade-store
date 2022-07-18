package com.db.tradestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceUtils {
    private static ServiceUtils instance;

    @Autowired
    private TradeService tradeService;

    @PostConstruct
    public void fillInstance() {
        instance = this;
    }

    public static TradeService getTradeService() {
        return instance.tradeService;
    }

}
