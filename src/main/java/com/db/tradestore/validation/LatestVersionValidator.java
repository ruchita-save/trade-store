package com.db.tradestore.validation;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@Component
public class LatestVersionValidator implements ConstraintValidator<LatestVersion , TradeDTO>{

    @Autowired
    TradeService tradeService;

    @Override
    public boolean isValid(TradeDTO trade, ConstraintValidatorContext constraintValidatorContext) {
        return trade!= null && tradeService.isLatestVersion(trade);
    }

}
