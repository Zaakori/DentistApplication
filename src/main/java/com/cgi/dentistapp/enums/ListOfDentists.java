package com.cgi.dentistapp.enums;

public enum ListOfDentists {
    GALERIUS_MAXIMUS("Galerius Maximus"),
    LUCRETIA_AMATA("Lucretia Amata"),
    EQUITIA_CLARA("Equitia Clara"),
    HORATIUS_FULGENCIO("Horatius Fulgencio"),
    MALLEOLUS_PAULUS("Malleolus Paulus");

    public final String label;

    private ListOfDentists(String label) {
        this.label = label;
    }
}
