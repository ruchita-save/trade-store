package com.db.tradestore.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

@Component
public class MaturityDateValidator implements ConstraintValidator<NotMatured, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return date != null && date.before(new Date());
    }

}
