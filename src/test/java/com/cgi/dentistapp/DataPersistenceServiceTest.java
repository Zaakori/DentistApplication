package com.cgi.dentistapp;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.service.DataPersistenceService;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// fancy Spring functionality is not needed here, so SpringJUnit4ClassRunner is not used
public class DataPersistenceServiceTest {

    @Test
    public void convertListOfEntitiesToListOfDTOs_correctBehaviour(){

        DataPersistenceService dataPersistenceService = new DataPersistenceService();

        List<DentistAppointmentEntity> listOfEntities = getDummyListOfEntities();
        List<DentistAppointmentDTO> listOfDTO = getDummyListOfDTOs();

        Assert.assertArrayEquals(listOfDTO.toArray(), dataPersistenceService.convertListOfEntitiesToListOfDTOs(listOfEntities).toArray());
    }

    @Test
    public void convertListOfDTOsToListOfEntities_correctBehaviour(){

        DataPersistenceService dataPersistenceService = new DataPersistenceService();

        List<DentistAppointmentEntity> listOfEntities = getDummyListOfEntities();
        List<DentistAppointmentDTO> listOfDTO = getDummyListOfDTOs();

        Assert.assertArrayEquals(listOfEntities.toArray(), dataPersistenceService.convertListOfDTOsToListOfEntities(listOfDTO).toArray());
    }

    // These last two methods are basically the same, why it is like that is explained at the top of DataPersistenceService.convertListOfEntitiesToListOfDTOs()
    private List<DentistAppointmentDTO> getDummyListOfDTOs(){

        Long id1 = 1L;
        Long id2 = 2L;

        String dentistName1 = "Equitia Clara";
        String dentistName2 = "Galerius Maximus";

        Date appointmentDateTime1 = new Date();
        Date appointmentDateTime2 = new Date();

        try{
            appointmentDateTime1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-02-05 10");
            appointmentDateTime2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 12");
        } catch (ParseException e){
            e.printStackTrace();
        }

        List<DentistAppointmentDTO> listOfDTO = new ArrayList<>();
        listOfDTO.add(new DentistAppointmentDTO(id1, dentistName1, appointmentDateTime1));
        listOfDTO.add(new DentistAppointmentDTO(id2, dentistName2, appointmentDateTime2));

        return listOfDTO;
    }

    private List<DentistAppointmentEntity> getDummyListOfEntities(){

        Long id1 = 1L;
        Long id2 = 2L;

        String dentistName1 = "Equitia Clara";
        String dentistName2 = "Galerius Maximus";

        Date appointmentDateTime1 = new Date();
        Date appointmentDateTime2 = new Date();

        try{
            appointmentDateTime1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-02-05 10");
            appointmentDateTime2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 12");
        } catch (ParseException e){
            e.printStackTrace();
        }

        List<DentistAppointmentEntity> listOfEntities = new ArrayList<>();
        listOfEntities.add(new DentistAppointmentEntity(id1, dentistName1, appointmentDateTime1));
        listOfEntities.add(new DentistAppointmentEntity(id2, dentistName2, appointmentDateTime2));

        return listOfEntities;
    }
}
