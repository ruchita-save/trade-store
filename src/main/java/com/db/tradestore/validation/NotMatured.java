package com.db.tradestore.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MaturityDateValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface NotMatured {

    public String message() default "Maturity date is older than today's date!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}
