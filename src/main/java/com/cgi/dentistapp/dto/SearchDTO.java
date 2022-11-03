package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchDTO {

    String dentistName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH")
    Date startingFromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH")
    Date endOnDate;

    public SearchDTO() {
    }

    public SearchDTO(String dentistName, Date startingFromDate, Date endOnDate) {
        this.dentistName = dentistName;
        this.startingFromDate = startingFromDate;
        this.endOnDate = endOnDate;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getStartingFromDate() {
        return startingFromDate;
    }

    public void setStartingFromDate(Date startingFromDate) {
        this.startingFromDate = startingFromDate;
    }

    public Date getEndOnDate() {
        return endOnDate;
    }

    public void setEndOnDate(Date endOnDate) {
        this.endOnDate = endOnDate;
    }
}
