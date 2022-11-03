package com.cgi.dentistapp.db.repository;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface DentistAppointmentRepository extends CrudRepository<DentistAppointmentEntity, Long> {
    @Query(value = "SELECT * FROM dentist_appointment ORDER BY appointment_datetime", nativeQuery = true)
    List<DentistAppointmentEntity> findAllAppointments();

    @Query(value = "SELECT id FROM dentist_appointment", nativeQuery = true)
    List<Integer> findAllIds();

    @Query(value = "SELECT * FROM DENTIST_APPOINTMENT " +
            "WHERE (dentist_name = :dentistName OR :dentistName IS NULL) " +
            "AND (appointment_datetime >= :startingFromDate OR :startingFromDate IS NULL) " +
            "AND (appointment_datetime <= :endOnDate OR :endOnDate IS NULL) " +
            "ORDER BY appointment_datetime", nativeQuery = true)
    List<DentistAppointmentEntity> search(@Param("dentistName") String dentistName, @Param("startingFromDate") Date startingFromDate, @Param("endOnDate") Date endOnDate);

    @Transactional
    @Modifying
    @Query(value = "UPDATE dentist_appointment SET dentist_name = ?1, appointment_datetime = ?2 WHERE id = ?3", nativeQuery = true)
    void updateEntity(String dentistName, Date appointmentDateTime, Long entityId);
}