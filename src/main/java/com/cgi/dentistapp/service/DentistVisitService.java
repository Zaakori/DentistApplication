package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.entity.DentistVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitRepository repo;

    public void addVisit(String dentistName, Date visitTime) {

        DentistVisitEntity entity = new DentistVisitEntity(dentistName, visitTime);
        repo.save(entity);
    }
}
