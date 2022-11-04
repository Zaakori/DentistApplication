package com.cgi.dentistapp.validation.interfaces;

import com.cgi.dentistapp.validation.implementation.ValidHourRangeImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// got it from https://www.baeldung.com/spring-mvc-custom-validator
@Constraint(validatedBy = ValidHourRangeImp.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidHourRange {

    String message() default "Invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}