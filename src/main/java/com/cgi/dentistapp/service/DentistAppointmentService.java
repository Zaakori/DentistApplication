package com.cgi.dentistapp.service;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import com.cgi.dentistapp.db.repository.DentistAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistAppointmentService {

    @Autowired
    private DentistAppointmentRepository repo;

    public void addAppointment(String dentistName, Date appointmentTime) {

        DentistAppointmentEntity entity = new DentistAppointmentEntity(dentistName, appointmentTime);
        repo.save(entity);
    }

    public List<DentistAppointmentEntity> getAllAppointments(){
        List<DentistAppointmentEntity> appointments = new ArrayList<>();
        repo.findAllAppointments().forEach(appointments::add);

        return appointments;
    }
}