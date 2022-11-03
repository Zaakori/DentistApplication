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

    /**
     * Creates a new Entity with provided parameters and saves it to the database through repository.
     *
     * @param dentistName dentist name for Entity's field 'dentistName'
     * @param appointmentDateTime date and time for Entity's field 'appointmentDateTime'
     */
    public void addAppointment(String dentistName, Date appointmentDateTime) {
        DentistAppointmentEntity entity = new DentistAppointmentEntity(dentistName, appointmentDateTime);
        repo.save(entity);
    }

    /**
     * Returns all Entities from the database through repository.
     *
     * @return List of Entity-s
     */
    public List<DentistAppointmentEntity> getAllAppointments(){
        return new ArrayList<>(repo.findAllAppointments());
    }

    /**
     * Gets all Entities from the database and returns them as DTO-s.
     *
     * @return List of DTO-s
     */
    public List<DentistAppointmentDTO> getAllAppointmentsAsDTO(){
        return convertListOfEntitiesToListOfDTOs(getAllAppointments());
    }

    /**
     * Returns all Ids from the database through repository.
     *
     * @return List of IDs
     */
    public List<Integer> getAllIds(){
        return repo.findAllIds();
    }

    /**
     * Returns list of searched DTO-s from the database through repository.
     * If parameter 'dentistName' value was "0", then sets the dentist name to null.
     *
     * @param dentistName name of the dentist that is being searched
     * @param startingFromDate starting date, searched date must be same (equal) or later (bigger)
     * @param endOnDate end date, searched date must be same (equal) or earlier (smaller)
     * @return List of DTO-s
     */
    public List<DentistAppointmentDTO> getAppointmentDTOsBySearch(String dentistName, Date startingFromDate, Date endOnDate){

        String name = dentistName;

        if(dentistName.equals("0")) name = null;

        return convertListOfEntitiesToListOfDTOs(repo.findAppointmentsBySearch(name, startingFromDate, endOnDate));
    }

    /**
     * Converts list of DTO-s into list of Entities and then one by one updates the Entities in the database through repository.
     *
     * @param listOfDTOs list of DTO-s
     */
    public void editAppointments(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> listOfEntities = convertListOfDTOsToListOfEntities(listOfDTOs);

        for(DentistAppointmentEntity entity : listOfEntities){
            repo.updateEntity(entity.getDentistName(), entity.getAppointmentDateTime(), entity.getId());
        }
    }

    /**
     * Makes an array of Strings by splitting stringOfIds using regex "," and then one by one
     * deletes Entities with corresponding ID-s.
     *
     * @param stringOfIds String that contains ID-s in it separated by comma, ex. "1,3,22"
     */
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
