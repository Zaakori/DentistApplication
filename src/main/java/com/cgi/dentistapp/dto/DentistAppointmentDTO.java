package com.cgi.dentistapp.dto;

import com.cgi.dentistapp.validation.interfaces.ValidDentist;
import com.cgi.dentistapp.validation.interfaces.ValidHourRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class DentistAppointmentDTO {

    Long id;

    @Size(min = 1, max = 50)
    @ValidDentist(message = "Please provide a name of a valid dentist.")
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH")
    @Future(message = "Date-time must be in the future!")
    @ValidHourRange(message = "Earliest appointment is at 9 o'clock and latest is 17 o'clock!")
    Date appointmentDateTime;

    public DentistAppointmentDTO() {
    }

    public DentistAppointmentDTO(String dentistName, Date appointmentDateTime) {
        this.dentistName = dentistName;
        this.appointmentDateTime = appointmentDateTime;
    }

    public DentistAppointmentDTO(Long id, String dentistName, Date appointmentDateTime) {
        this.id = id;
        this.dentistName = dentistName;
        this.appointmentDateTime = appointmentDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(Date appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    @Override
    public boolean equals(Object o){

        if(o == this){
            return true;
        }

        if(!(o instanceof DentistAppointmentDTO)){
            return false;
        }

        DentistAppointmentDTO other = (DentistAppointmentDTO) o;

        return this.dentistName.equals(other.dentistName) && this.appointmentDateTime.equals(other.appointmentDateTime);
    }

    @Override
    public int hashCode(){

        int result = 17;

        if(dentistName != null) {
            result = 31 * result * dentistName.hashCode();
        }

        if(appointmentDateTime != null){
            result = 31 * result * appointmentDateTime.hashCode();
        }

        return result;
    }
}
