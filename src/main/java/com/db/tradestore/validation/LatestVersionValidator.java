package com.db.tradestore.validation;

import com.db.tradestore.model.Trade;
import com.db.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatestVersionValidator implements ConstraintValidator<LatestVersion , Trade>{

    @Autowired
    private TradeService tradeService;

    @Override
    public boolean isValid(Trade trade, ConstraintValidatorContext constraintValidatorContext) {
        return trade!= null && tradeService.isLatestVersion(trade);
    }
}
