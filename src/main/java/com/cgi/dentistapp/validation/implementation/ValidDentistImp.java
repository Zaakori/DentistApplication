package com.cgi.dentistapp.validation.implementation;

import com.cgi.dentistapp.enums.DentistNames;
import com.cgi.dentistapp.validation.interfaces.ValidDentist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidDentistImp implements ConstraintValidator<ValidDentist, String> {

    @Override
    public void initialize(ValidDentist presentOrFuture) {
    }

    @Override
    public boolean isValid(String dentistName, ConstraintValidatorContext cxt) {
        List<String> listOfDentists = DentistNames.getListOfDentists();
        return listOfDentists.contains(dentistName);
    }
}
