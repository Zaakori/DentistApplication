package com.cgi.dentistapp.db.repository;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface DentistAppointmentRepository extends CrudRepository<DentistAppointmentEntity, Long> {
    @Query(value = "SELECT * FROM dentist_appointment ORDER BY appointment_time", nativeQuery = true)
    List<DentistAppointmentEntity> findAllAppointments();

    @Query(value = "SELECT id FROM dentist_appointment", nativeQuery = true)
    List<Integer> findAllIds();

    @Transactional
    @Modifying
    @Query(value = "UPDATE dentist_appointment SET dentist_name = ?1, appointment_time = ?2 WHERE id = ?3", nativeQuery = true)
    void updateEntity(String dentistName, Date appointmentTime, Long entityId);
}