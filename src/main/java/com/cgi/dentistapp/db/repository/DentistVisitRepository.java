package com.cgi.dentistapp.db.repository;

import com.cgi.dentistapp.db.entity.DentistVisitEntity;
import org.springframework.data.repository.CrudRepository;
public interface DentistVisitRepository extends CrudRepository<DentistVisitEntity, Long>
{
}