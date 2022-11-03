package com.cgi.dentistapp.service;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import com.cgi.dentistapp.db.repository.DentistAppointmentRepository;
import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class DataPersistenceService {

    @Autowired
    private DentistAppointmentRepository repo;

    public void addAppointment(String dentistName, Date appointmentTime) {
        DentistAppointmentEntity entity = new DentistAppointmentEntity(dentistName, appointmentTime);
        repo.save(entity);
    }

    public List<DentistAppointmentEntity> getAllAppointments(){
        return new ArrayList<>(repo.findAllAppointments());
    }

    public List<DentistAppointmentDTO> getAllAppointmentsAsDTO(){
        return convertListOfEntitiesToListOfDTOs(getAllAppointments());
    }

    public List<Integer> getAllIds(){
        return repo.findAllIds();
    }

    public List<DentistAppointmentDTO> getAppointmentsBySearch(String dentistName, Date startingFromDate, Date endOnDate){

        String name = dentistName;

        if(dentistName.equals("0")) name = null;

        return convertListOfEntitiesToListOfDTOs(repo.findAppointmentsBySearch(name, startingFromDate, endOnDate));
    }

    public void editAppointments(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> listOfEntities = convertListOfDTOsToListOfEntities(listOfDTOs);

        for(DentistAppointmentEntity entity : listOfEntities){
            repo.updateEntity(entity.getDentistName(), entity.getAppointmentDateTime(), entity.getId());
        }
    }

    public void deleteAppointments(String stringOfIds){

        String[] ids = stringOfIds.split(",");

        for(String id : ids){
            repo.delete(Long.parseLong(id));
        }
    }

    /* These last two methods are basically exactly the same, and they could be changed to just one method that takes in generics, but
       that would be a mistake. Right now it happened so that DTO and Entity are exactly the same, and can be basically used interchangeably, but
       in the future that might change and if it does change then one method with generics will break. So there should be two different methods that
       convert one object into another according to circumstances. */
    public List<DentistAppointmentDTO> convertListOfEntitiesToListOfDTOs(List<DentistAppointmentEntity> listOfEntities){

        List<DentistAppointmentDTO> listOfDTOs = new ArrayList<>();

        for(DentistAppointmentEntity entity : listOfEntities){
            DentistAppointmentDTO DTO = new DentistAppointmentDTO(entity.getId(), entity.getDentistName(), entity.getAppointmentDateTime());
            listOfDTOs.add(DTO);
        }

        return listOfDTOs;
    }

    public List<DentistAppointmentEntity> convertListOfDTOsToListOfEntities(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> listOfEntities = new ArrayList<>();

        for(DentistAppointmentDTO DTO : listOfDTOs){
            DentistAppointmentEntity entity = new DentistAppointmentEntity(DTO.getId(), DTO.getDentistName(), DTO.getAppointmentDateTime());
            listOfEntities.add(entity);
        }

        return listOfEntities;
    }
}
