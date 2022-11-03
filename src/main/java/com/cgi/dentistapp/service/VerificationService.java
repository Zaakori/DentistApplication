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
    private DataPersistenceService dataPersistenceService;

    public boolean checkIfAppointmentIsAvailable(String dentistName, Date appointmentTime){

        Set<DentistAppointmentEntity> appointments = new HashSet<>(dataPersistenceService.getAllAppointments());
        DentistAppointmentEntity newEntity = new DentistAppointmentEntity(dentistName, appointmentTime);

        return !appointments.contains(newEntity);
    }

    public boolean checkIfAppointmentListIsValid(List<DentistAppointmentDTO> listOfDTOs){

        Set<DentistAppointmentDTO> appointmentSet = new HashSet<>();

        for(DentistAppointmentDTO appointment : listOfDTOs){
            if(appointmentSet.contains(appointment)){
                return false;
            } else {
                appointmentSet.add(appointment);
            }
        }

        return true;
    }

    public boolean checkIfIdsAreValid(String idsString){

        String[] ids = idsString.split(",");
        Set<Integer> setOfValidIds = new HashSet<>(dataPersistenceService.getAllIds());

        try{
            for(String id : ids){
                if(!setOfValidIds.contains(Integer.parseInt(id))){
                    return false;
                }
            }
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }
}
