package com.cgi.dentistapp.service;

import com.cgi.dentistapp.db.entity.DentistVisitEntity;
import com.cgi.dentistapp.db.repository.DentistVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitRepository repo;

    public void addVisit(String dentistName, Date visitTime) {

        DentistVisitEntity entity = new DentistVisitEntity(dentistName, visitTime);
        repo.save(entity);
    }

    public List<DentistVisitEntity> getAllVisits(){
        List<DentistVisitEntity> visits = new ArrayList<>();
        repo.findAll().forEach(visits::add);

        return visits;
    }
}
