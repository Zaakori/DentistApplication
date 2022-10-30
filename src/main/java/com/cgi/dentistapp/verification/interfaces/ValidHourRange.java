package com.cgi.dentistapp.verification.interfaces;

import com.cgi.dentistapp.verification.implementation.ValidHourRangeImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = ValidHourRangeImp.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ValidHourRange {

    String message() default "Invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}