package com.cgi.dentistapp.dto;

import java.util.ArrayList;
import java.util.List;

public class ListOfDentistAppointmentsDTO {

    private List<DentistAppointmentDTO> appointments;

    public ListOfDentistAppointmentsDTO() {
        this.appointments = new ArrayList<>();
    }

    public ListOfDentistAppointmentsDTO(List<DentistAppointmentDTO> appointments) {
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
