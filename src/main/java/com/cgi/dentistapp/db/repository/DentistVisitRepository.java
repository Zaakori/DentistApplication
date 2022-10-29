package com.cgi.dentistapp.db.repository;

import com.cgi.dentistapp.db.entity.DentistVisitEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DentistVisitRepository extends CrudRepository<DentistVisitEntity, Long> {
    @Query(value = "SELECT * FROM DENTIST_VISIT ORDER BY VISIT_TIME", nativeQuery = true)
    List<DentistVisitEntity> findAllAppointments();
}