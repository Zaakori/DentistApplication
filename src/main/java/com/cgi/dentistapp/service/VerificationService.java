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

    // this method is for checking appointment availability for one new appointment against all appointments taken from database
    public boolean checkIfAppointmentIsAvailable(String dentistName, Date appointmentTime){

        Set<DentistAppointmentEntity> appointments = new HashSet<>(service.getAllAppointments());
        DentistAppointmentEntity newEntity = new DentistAppointmentEntity(dentistName, appointmentTime);

        return !appointments.contains(newEntity);
    }

    // this method is checking appointment availability too, but we get all the appointments right away and just check if there are duplicates
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

        String[] ids = appointmentIds.split(",");
        Set<Integer> setOfValidIds = new HashSet<>(service.getAllIds());

        try{
            for(String nonValidatedId : ids){
                if(!setOfValidIds.contains(Integer.parseInt(nonValidatedId))){
                    return false;
                }
            }
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }
}
