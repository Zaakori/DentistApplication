package com.cgi.dentistapp.service;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VerificationService {

    @Autowired
    private DataPersistenceService service;

    public boolean checkIfAppointmentIsAvailable(String dentistName, Date appointmentTime){

        Set<DentistAppointmentEntity> appointments = new HashSet<>(service.getAllAppointments());
        DentistAppointmentEntity newEntity = new DentistAppointmentEntity(dentistName, appointmentTime);

        return !appointments.contains(newEntity);
    }

    public boolean checkIfAppointmentListIsAvailable(List<DentistAppointmentDTO> listOfDTOs){

        List<DentistAppointmentEntity> inputEntityList = service.convertListOfDTOsToListOfEntities(listOfDTOs);
        Set<DentistAppointmentEntity> inputEntitySet = new HashSet<>();

        for(DentistAppointmentEntity inputEntity : inputEntityList){
            if(inputEntitySet.contains(inputEntity)){
                return false;
            } else {
                inputEntitySet.add(inputEntity);
            }
        }

        return true;
    }

    public boolean checkIfIdsAreValid(String appointmentIds){

        Set<Integer> setOfValidIds = new HashSet<>(service.getAllIds());

        String[] ids = appointmentIds.split(",");

        for(String nonValidatedId : ids){
            if(!setOfValidIds.contains(Integer.parseInt(nonValidatedId))){
                return false;
            }
        }

        return true;
    }
}
