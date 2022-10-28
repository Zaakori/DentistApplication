package com.cgi.dentistapp.dto;

import com.cgi.dentistapp.verification.interfaces.PresentOrFuture;
import com.cgi.dentistapp.verification.interfaces.ValidDentist;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class DentistVisitDTO {

    @Size(min = 1, max = 50)
    @ValidDentist
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PresentOrFuture
    Date visitTime;

    public DentistVisitDTO() {
    }

    public DentistVisitDTO(String dentistName, Date visitTime) {
        this.dentistName = dentistName;
        this.visitTime = visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
}
