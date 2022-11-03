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

    @Column(name="APPOINTMENT_DATETIME")
    private Date appointmentDateTime;

    public DentistAppointmentEntity() {
    }

    public DentistAppointmentEntity(String dentistName, Date appointmentDateTime) {
        this.dentistName = dentistName;
        this.appointmentDateTime = appointmentDateTime;
    }

    public DentistAppointmentEntity(Long id, String dentistName, Date appointmentDateTime) {
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

        if(!(o instanceof DentistAppointmentEntity)){
            return false;
        }

        DentistAppointmentEntity other = (DentistAppointmentEntity) o;

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
