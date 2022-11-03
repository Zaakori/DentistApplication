package com.cgi.dentistapp.validation.implementation;

import com.cgi.dentistapp.validation.interfaces.ValidHourRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class ValidHourRangeImp implements ConstraintValidator<ValidHourRange, Date> {

    @Override
    public void initialize(ValidHourRange presentOrFuture) {
    }

    /**
     * Checks if hour in the parameter pickedDate is between (or equal to) 09:00 and 17:00.
     *
     * @param pickedDate Date, that we validate the hour of
     * @return returns 'true' if hour is between (or equal to) 9 and 17, if not then 'false'
     */
    @Override
    public boolean isValid(Date pickedDate, ConstraintValidatorContext cxt) {

        if(pickedDate == null) return false;

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
