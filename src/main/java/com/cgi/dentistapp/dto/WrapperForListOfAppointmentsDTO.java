package com.cgi.dentistapp.dto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class WrapperForListOfAppointmentsDTO {

    @Valid
    private List<DentistAppointmentDTO> appointments;

    public WrapperForListOfAppointmentsDTO() {
        this.appointments = new ArrayList<>();
    }

    public WrapperForListOfAppointmentsDTO(List<DentistAppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public List<DentistAppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<DentistAppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(DentistAppointmentDTO appointment){
        appointments.add(appointment);
    }

    public boolean empty(){
        return appointments.isEmpty();
    }
}
