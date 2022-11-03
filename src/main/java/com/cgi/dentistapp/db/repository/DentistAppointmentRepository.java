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

    /**
     * Returns all Entities (rows 'id', 'dentist_name', 'appointment_datetime') that are present in database table dentist_appointment.
     *
     * @return  List of Entity-s
     */
    @Query(value = "SELECT id, dentist_name, appointment_datetime FROM dentist_appointment ORDER BY appointment_datetime", nativeQuery = true)
    List<DentistAppointmentEntity> findAllAppointments();

    /**
     * Returns all IDs (ID column) that are present in database table dentist_appointment.
     *
     * @return  List of IDs
     */
    @Query(value = "SELECT id FROM dentist_appointment", nativeQuery = true)
    List<Integer> findAllIds();

    /**
     * Returns Entities (rows 'id', 'dentist_name', 'appointment_datetime') from database table dentist_appointment based on a search.
     * The search consists of parameters String 'dentistName', Date 'startingFromDate' and Date 'endOnDate'.
     * It is possible to provide all three search parameters or no parameters (null values) or any combination of these, the query is made dynamically
     * according to what parameters are present.
     *
     * @param dentistName name of the dentist that is being searched
     * @param startingFromDate starting date, searched Entities must be same (equal) or later (bigger)
     * @param endOnDate end date, searched Entities must be same (equal) or earlier (smaller)
     * @return  List of IDs
     */
    @Query(value = "SELECT id, dentist_name, appointment_datetime FROM DENTIST_APPOINTMENT " +
            "WHERE (dentist_name = :dentistName OR :dentistName IS NULL) " +
            "AND (appointment_datetime >= :startingFromDate OR :startingFromDate IS NULL) " +
            "AND (appointment_datetime <= :endOnDate OR :endOnDate IS NULL) " +
            "ORDER BY appointment_datetime", nativeQuery = true)
    List<DentistAppointmentEntity> findAppointmentsBySearch(@Param("dentistName") String dentistName, @Param("startingFromDate") Date startingFromDate, @Param("endOnDate") Date endOnDate);

    /**
     * Updates rows dentist_name and appointment_datetime in database table dentist_appointment using provided parameters.
     *
     * @param dentistName new value for dentist_name
     * @param appointmentDateTime new value for appointment_datetime
     * @param id id, that is used to determine which Entity (row) is being updated
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE dentist_appointment SET dentist_name = ?1, appointment_datetime = ?2 WHERE id = ?3", nativeQuery = true)
    void updateEntity(String dentistName, Date appointmentDateTime, Long id);
}