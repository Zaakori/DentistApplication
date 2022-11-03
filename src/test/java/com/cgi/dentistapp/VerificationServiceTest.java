package com.cgi.dentistapp;

import com.cgi.dentistapp.db.repository.DentistAppointmentRepository;
import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.service.DataPersistenceService;
import com.cgi.dentistapp.service.VerificationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// ADD normal and understandable assert failure messages
// TODO

@SpringBootTest
@RunWith( SpringJUnit4ClassRunner.class )
public class VerificationServiceTest {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private DataPersistenceService dataPersistenceService;

    @Autowired
    private DentistAppointmentRepository repo;

    // INTEGRATION TEST
    @Test
    public void checkIfAppointmentIsAvailable_verifyAppointmentAgainstEmptyDB(){
        repo.deleteAll();

        String dentistName = "Equitia Clara";
        Date appointmentDateTime = new Date();

        try{
            appointmentDateTime = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 10");
        } catch (ParseException e){
            e.printStackTrace();
        }

        Assert.assertTrue(verificationService.checkIfAppointmentIsAvailable(dentistName, appointmentDateTime));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfAppointmentIsAvailable_verifyDifferentAppointmentAgainstNonEmptyDB(){
        repo.deleteAll();

        String dentistName1 = "Equitia Clara";
        String dentistName2 = "Horatius Fulgencio";
        String registeredDentistName = "Galerius Maximus";

        Date appointmentDateTime1 = new Date();
        Date appointmentDateTime2 = new Date();
        Date registeredAppointmentDateTime = new Date();

        try{
            appointmentDateTime1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-02-05 10");
            appointmentDateTime2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 12");
            registeredAppointmentDateTime = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-02-14 16");
        } catch (ParseException e){
            e.printStackTrace();
        }

        dataPersistenceService.addAppointment(dentistName1, appointmentDateTime1);
        dataPersistenceService.addAppointment(dentistName2, appointmentDateTime2);

        Assert.assertTrue(verificationService.checkIfAppointmentIsAvailable(registeredDentistName, registeredAppointmentDateTime));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfAppointmentIsAvailable_verifySameAppointmentAgainstNonEmptyDB(){
        repo.deleteAll();

        String dentistName = "Equitia Clara";
        Date appointmentDateTime = new Date();

        try{
            appointmentDateTime = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 10");
        } catch (ParseException e){
            e.printStackTrace();
        }

        dataPersistenceService.addAppointment(dentistName, appointmentDateTime);

        Assert.assertFalse(verificationService.checkIfAppointmentIsAvailable(dentistName, appointmentDateTime));
    }

    @Test
    public void checkIfAppointmentListIsAvailable_allDifferentAppointments(){

        String dentistName1 = "Galerius Maximus";
        String dentistName2 = "Malleolus Paulus";

        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();

        try{
            date1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2022-12-21 16");
            date2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-01-25 13");
            date3 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2022-15-24 11");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<DentistAppointmentDTO> listOfDTOs = new ArrayList<>();
        listOfDTOs.add(new DentistAppointmentDTO(dentistName1, date1));
        listOfDTOs.add(new DentistAppointmentDTO(dentistName2, date2));
        listOfDTOs.add(new DentistAppointmentDTO(dentistName2, date3));

        Assert.assertTrue("Incorrect return value for all different appointments", verificationService.checkIfAppointmentListIsAvailable(listOfDTOs));
    }

    @Test
    public void checkIfAppointmentListIsAvailable_twoEqualAppointments(){

        String dentistName1 = "Galerius Maximus";
        String dentistName2 = "Malleolus Paulus";

        Date date1 = new Date();
        Date date2 = new Date();

        try{
            date1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2022-12-24 16");
            date2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-01-25 13");
        } catch (ParseException e) {
            System.out.println("EXCEPTION while parsing dates.");
        }

        List<DentistAppointmentDTO> listOfDTOs = new ArrayList<>();
        listOfDTOs.add(new DentistAppointmentDTO(dentistName1, date1));
        listOfDTOs.add(new DentistAppointmentDTO(dentistName2, date2));
        listOfDTOs.add(new DentistAppointmentDTO(dentistName2, date2));

        Assert.assertFalse("Incorrect return value for two same appointments", verificationService.checkIfAppointmentListIsAvailable(listOfDTOs));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfIdsAreValid_allIdsPresentInDB(){
        repo.deleteAll();

        List<Integer> verifiedIds = setupForIdTesting();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < verifiedIds.size(); i++){
            sb.append(verifiedIds.get(i));

            if(i != verifiedIds.size() - 1){
                sb.append(",");
            }
        }

        String ids = sb.toString();

        Assert.assertTrue(verificationService.checkIfIdsAreValid(ids));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfIdsAreValid_IdNotPresentInDB(){
        repo.deleteAll();

        List<Integer> verifiedIds = setupForIdTesting();
        String ids = Integer.toString(verifiedIds.get(verifiedIds.size() - 1) + 1);

        Assert.assertFalse(verificationService.checkIfIdsAreValid(ids));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfIdsAreValid_idContainsNonNumerics(){
        repo.deleteAll();

        setupForIdTesting();

        String ids = "q";

        Assert.assertFalse(verificationService.checkIfIdsAreValid(ids));
    }

    private List<Integer> setupForIdTesting(){

        String dentistName1 = "Equitia Clara";
        String dentistName2 = "Horatius Fulgencio";
        String dentistName3 = "Galerius Maximus";

        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();

        try{
            date1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2022-12-24 16");
            date2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-01-25 13");
            date3 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2022-15-24 11");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dataPersistenceService.addAppointment(dentistName1, date1);
        dataPersistenceService.addAppointment(dentistName2, date2);
        dataPersistenceService.addAppointment(dentistName3, date3);

        return repo.findAllIds();
    }
}
