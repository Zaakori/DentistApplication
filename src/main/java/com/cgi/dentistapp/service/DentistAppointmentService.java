package com.cgi.dentistapp.service;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import com.cgi.dentistapp.db.repository.DentistAppointmentRepository;
import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.dto.ListOfDentistAppointmentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistAppointmentService {

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

    public List<DentistAppointmentDTO> getAllAppointmentsAsDTO(){

        List<DentistAppointmentEntity> entityList = getAllAppointments();
        List<DentistAppointmentDTO> DTOList = new ArrayList<>();

        for(DentistAppointmentEntity entity : entityList){
            DentistAppointmentDTO DTO = new DentistAppointmentDTO(entity.getId(), entity.getDentistName(), entity.getAppointmentTime());
            DTOList.add(DTO);
        }

        return DTOList;
    }

    private List<DentistAppointmentEntity> convertListOfDTOsToListOfEntities(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> listOfEntities = new ArrayList<>();

        for(DentistAppointmentDTO DTO : listOfDTOs){
            DentistAppointmentEntity entity = new DentistAppointmentEntity(DTO.getId(), DTO.getDentistName(), DTO.getAppointmentTime());
            listOfEntities.add(entity);
        }

        return listOfEntities;
    }
}
