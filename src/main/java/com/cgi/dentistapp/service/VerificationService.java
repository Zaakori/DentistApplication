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

    /**
     * Creates a new Entity with provided parameters and checks if the same exact Entity (with same dentistName and appointmentDateTime)
     * is already present in the database.
     *
     * @param dentistName dentist name for Entity's field 'dentistName'
     * @param appointmentDateTime date and time for Entity's field 'appointmentDateTime'
     * @return returns 'true' if no same Entity is present, 'false' is same Entity is already present in the database
     */
    public boolean checkIfAppointmentIsAvailable(String dentistName, Date appointmentDateTime){

        Set<DentistAppointmentEntity> appointments = new HashSet<>(dataPersistenceService.getAllAppointments());
        DentistAppointmentEntity newEntity = new DentistAppointmentEntity(dentistName, appointmentDateTime);

        return !appointments.contains(newEntity);
    }

    /**
     * Checks given list of DTOs for duplicates. A duplicate in this case is a DTO with the same 'dentistName' and 'appointmentDateTime'.
     *
     * @param listOfDTOs list of DTO-s
     * @return returns 'true' if no duplicates have been found, 'false' list contains duplicates
     */
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

    /**
     * Checks whether all ID-s in given String are present in the database.
     *
     * @param idsString String that contains ID-s in it separated by comma, ex. "1,3,22"
     * @return returns 'true' if all ID-s are present, 'false' if any ID-s are not present in the database
     */
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
