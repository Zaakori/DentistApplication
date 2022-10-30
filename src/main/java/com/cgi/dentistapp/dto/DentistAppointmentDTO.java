package com.cgi.dentistapp.dto;

import com.cgi.dentistapp.verification.interfaces.ValidDentist;
import com.cgi.dentistapp.verification.interfaces.ValidHourRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class DentistAppointmentDTO {

    @Size(min = 1, max = 50)
    @ValidDentist(message = "Please provide a name of a valid dentist.")
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH")
    @Future(message = "Date-time must be in the future!")
    @ValidHourRange(message = "Earliest appointment is at 9 o'clock and latest is 17 o'clock!")
    Date appointmentTime;

    public DentistAppointmentDTO() {
    }

    public DentistAppointmentDTO(String dentistName, Date appointmentTime) {
        this.dentistName = dentistName;
        this.appointmentTime = appointmentTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
