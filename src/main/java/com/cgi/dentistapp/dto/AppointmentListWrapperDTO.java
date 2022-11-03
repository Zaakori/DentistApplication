package com.cgi.dentistapp.dto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class AppointmentListWrapperDTO {

    @Valid
    private List<DentistAppointmentDTO> appointments;

    public AppointmentListWrapperDTO() {
        this.appointments = new ArrayList<>();
    }

    public AppointmentListWrapperDTO(List<DentistAppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public List<DentistAppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<DentistAppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public boolean empty(){
        return appointments.isEmpty();
    }
}
