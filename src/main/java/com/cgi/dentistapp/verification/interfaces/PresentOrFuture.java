package com.cgi.dentistapp.verification.interfaces;


import com.cgi.dentistapp.verification.implementation.PresentOrFutureImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// taken from https://blog.tericcabrel.com/write-custom-validator-for-body-request-in-spring-boot/

@Constraint(validatedBy = PresentOrFutureImp.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface PresentOrFuture {

    String message() default "Date is not today or in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
