package com.cgi.dentistapp.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dentist_appointment")
public class DentistAppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="DENTIST_NAME")
    private String dentistName;

    @Column(name="APPOINTMENT_TIME")
    private Date appointmentTime;

    public DentistAppointmentEntity() {
    }

    public DentistAppointmentEntity(String dentistName, Date appointmentTime) {
        this.dentistName = dentistName;
        this.appointmentTime = appointmentTime;
    }

    public DentistAppointmentEntity(Long id, String dentistName, Date appointmentTime) {
        this.id = id;
        this.dentistName = dentistName;
        this.appointmentTime = appointmentTime;
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

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
