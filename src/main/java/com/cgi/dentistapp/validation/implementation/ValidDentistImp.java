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

    /**
     * Checks if given dentist name is one of the dentist names present in the enum DentistNames.
     *
     * @param dentistName name of the dentist
     * @return returns 'true' if the dentist name is present, 'false' if it is not present
     */
    @Override
    public boolean isValid(String dentistName, ConstraintValidatorContext cxt) {
        List<String> listOfDentists = DentistNames.getListOfDentists();
        return listOfDentists.contains(dentistName);
    }
}
