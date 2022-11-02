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
        List<DentistAppointmentEntity> appointments = new ArrayList<>();
        repo.findAllAppointments().forEach(appointments::add);

        return appointments;
    }

    public List<Integer> getAllIds(){
        return repo.findAllIds();
    }

    public void editAppointments(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> listOfEntities = convertListOfDTOsToListOfEntities(listOfDTOs);

        for(DentistAppointmentEntity entity : listOfEntities){
            repo.updateEntity(entity.getDentistName(), entity.getAppointmentTime(), entity.getId());
        }
    }

    public void deleteAppointments(String appointmentIds){

        String[] ids = appointmentIds.split(",");

        for(String id : ids){
            repo.delete(Long.parseLong(id));
        }
    }

    public List<DentistAppointmentDTO> search(String dentistName, Date startingFromDate, Date endOnDate){

        String name = dentistName;

        if(dentistName.equals("0")){
            name = null;
        }

        List<DentistAppointmentEntity> listOfEntities = repo.search(name, startingFromDate, endOnDate);

        return convertListOfEntitiesToListOfDTOs(listOfEntities);
    }

    public List<DentistAppointmentDTO> getAllAppointmentsAsDTO(){
        return convertListOfEntitiesToListOfDTOs(getAllAppointments());
    }

    // these two last methods are exactly the same, I should redo them into one method using generics
    public List<DentistAppointmentDTO> convertListOfEntitiesToListOfDTOs(List<DentistAppointmentEntity> listOfEntities){

        List<DentistAppointmentDTO> listOfDTOs = new ArrayList<>();

        for(DentistAppointmentEntity entity : listOfEntities){
            DentistAppointmentDTO DTO = new DentistAppointmentDTO(entity.getId(), entity.getDentistName(), entity.getAppointmentTime());
            listOfDTOs.add(DTO);
        }

        return listOfDTOs;
    }

    public List<DentistAppointmentEntity> convertListOfDTOsToListOfEntities(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> listOfEntities = new ArrayList<>();

        for(DentistAppointmentDTO DTO : listOfDTOs){
            DentistAppointmentEntity entity = new DentistAppointmentEntity(DTO.getId(), DTO.getDentistName(), DTO.getAppointmentTime());
            listOfEntities.add(entity);
        }

        return listOfEntities;
    }
}
