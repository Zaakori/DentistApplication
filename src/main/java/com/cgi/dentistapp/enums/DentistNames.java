package com.cgi.dentistapp.enums;

import java.util.ArrayList;
import java.util.List;

public enum DentistNames {
    GALERIUS_MAXIMUS("Galerius Maximus"),
    LUCRETIA_AMATA("Lucretia Amata"),
    EQUITIA_CLARA("Equitia Clara"),
    HORATIUS_FULGENCIO("Horatius Fulgencio"),
    MALLEOLUS_PAULUS("Malleolus Paulus");

    public final String label;

    DentistNames(String label) {
        this.label = label;
    }

    public static List<String> getListOfDentists(){

        List<String> listOfDentists = new ArrayList<>();

        for(DentistNames dentist : values()){
            listOfDentists.add(dentist.label);
        }

        return listOfDentists;
    }
}
