package com.cgi.dentistapp.db.repository;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DentistAppointmentRepository extends CrudRepository<DentistAppointmentEntity, Long> {
    @Query(value = "SELECT * FROM DENTIST_APPOINTMENT ORDER BY APPOINTMENT_TIME", nativeQuery = true)
    List<DentistAppointmentEntity> findAllAppointments();
}