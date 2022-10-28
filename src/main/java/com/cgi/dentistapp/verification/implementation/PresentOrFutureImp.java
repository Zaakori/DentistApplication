package com.cgi.dentistapp.verification.implementation;

import com.cgi.dentistapp.verification.interfaces.PresentOrFuture;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

// taken from https://blog.tericcabrel.com/write-custom-validator-for-body-request-in-spring-boot/

public class PresentOrFutureImp implements ConstraintValidator<PresentOrFuture, Date> {

    @Override
    public void initialize(PresentOrFuture presentOrFuture) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext cxt) {

        try{
            Date today = new Date();

            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");

            boolean isToday = simpleFormat.format(today).equals(simpleFormat.format(date));
            boolean isFuture = date.after(today);


            return isToday || isFuture;
        } catch (Exception e){
            return false;
        }
    }
}
