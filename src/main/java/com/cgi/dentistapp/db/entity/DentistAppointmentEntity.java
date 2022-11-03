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

    @Override
    public boolean equals(Object o){

        if(o == this){
            return true;
        }

        if(!(o instanceof DentistAppointmentEntity)){
            return false;
        }

        DentistAppointmentEntity other = (DentistAppointmentEntity) o;

        return this.dentistName.equals(other.dentistName) && this.appointmentTime.equals(other.appointmentTime);
    }

    @Override
    public int hashCode(){

        int result = 17;

        if(dentistName != null) {
            result = 31 * result * dentistName.hashCode();
        }

        if(appointmentTime != null){
            result = 31 * result * appointmentTime.hashCode();
        }

        return result;
    }
}
