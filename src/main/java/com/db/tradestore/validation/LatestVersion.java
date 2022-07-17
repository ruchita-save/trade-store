package com.db.tradestore.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LatestVersionValidator.class)
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
public @interface LatestVersion {

    public String message() default "There is already latest version of trade available in Trade Store!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}
