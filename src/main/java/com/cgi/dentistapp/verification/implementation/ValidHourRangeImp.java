package com.cgi.dentistapp.verification.implementation;

import com.cgi.dentistapp.enums.ListOfDentists;
import com.cgi.dentistapp.verification.interfaces.ValidHourRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;


public class ValidHourRangeImp implements ConstraintValidator<ValidHourRange, Date> {

    @Override
    public void initialize(ValidHourRange presentOrFuture) {
    }

    @Override
    public boolean isValid(Date pickedDate, ConstraintValidatorContext cxt) {

        int hours = getHours(pickedDate);
        int min = 9;
        int max = 17;

        return (hours >= min) && (hours <= max);
    }

    private int getHours(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
