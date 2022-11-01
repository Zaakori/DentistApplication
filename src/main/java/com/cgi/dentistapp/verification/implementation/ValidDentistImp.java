package com.cgi.dentistapp.verification.implementation;

import com.cgi.dentistapp.enums.ListOfDentists;
import com.cgi.dentistapp.verification.interfaces.ValidDentist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class ValidDentistImp implements ConstraintValidator<ValidDentist, String> {

    @Override
    public void initialize(ValidDentist presentOrFuture) {
    }

    @Override
    public boolean isValid(String dentistName, ConstraintValidatorContext cxt) {

        List<String> listOfDentists = ListOfDentists.getListOfDentists();
        return listOfDentists.contains(dentistName);
    }
}
