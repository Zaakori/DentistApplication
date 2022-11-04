package com.cgi.dentistapp;

import com.cgi.dentistapp.db.entity.DentistAppointmentEntity;
import com.cgi.dentistapp.db.repository.DentistAppointmentRepository;
import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.service.DataPersistenceService;
import com.cgi.dentistapp.service.VerificationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// got Thymeleaf with MockMvc testing part from https://rieckpil.de/test-thymeleaf-controller-endpoints-with-spring-boot-and-mockmvc/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class DentistAppApplicationTests {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private DataPersistenceService dataPersistenceService;

    @Autowired
    private DentistAppointmentRepository repo;

    @Autowired
    private MockMvc mockMvc;





    // Tests for class VerificationService

    // INTEGRATION TEST
    @Test
    public void checkIfAppointmentIsAvailable_verifyAppointmentAgainstEmptyDB(){
        repo.deleteAll();

        String dentistName = "Equitia Clara";
        Date date = new Date();

        try{
            date = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 10");
        } catch (ParseException e){
            e.printStackTrace();
        }

        Assert.assertTrue(verificationService.checkIfAppointmentIsAvailable(dentistName, date));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfAppointmentIsAvailable_verifyDifferentAppointmentAgainstNonEmptyDB(){
        repo.deleteAll();

        String dentistName1 = "Equitia Clara";
        String dentistName2 = "Horatius Fulgencio";
        String registeredDentistName = "Galerius Maximus";

        Date date1 = new Date();
        Date date2 = new Date();
        Date registeredDate = new Date();

        try{
            date1 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-02-05 10");
            date2 = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 12");
            registeredDate = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-02-14 16");
        } catch (ParseException e){
            e.printStackTrace();
        }

        dataPersistenceService.addAppointment(dentistName1, date1);
        dataPersistenceService.addAppointment(dentistName2, date2);

        Assert.assertTrue(verificationService.checkIfAppointmentIsAvailable(registeredDentistName, registeredDate));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfAppointmentIsAvailable_verifySameAppointmentAgainstNonEmptyDB(){
        repo.deleteAll();

        String dentistName = "Equitia Clara";
        Date date = new Date();

        try{
            date = new SimpleDateFormat("yyyy-MM-dd hh").parse("2023-03-05 10");
        } catch (ParseException e){
            e.printStackTrace();
        }

        dataPersistenceService.addAppointment(dentistName, date);

        Assert.assertFalse(verificationService.checkIfAppointmentIsAvailable(dentistName, date));
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

        Assert.assertTrue("Incorrect return value for all different appointments", verificationService.checkIfAppointmentListIsValid(listOfDTOs));
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

        Assert.assertFalse("Incorrect return value for two same appointments", verificationService.checkIfAppointmentListIsValid(listOfDTOs));
    }

    // INTEGRATION TEST
    @Test
    public void checkIfIdsAreValid_allIdsPresentInDB(){
        repo.deleteAll();

        setupForIdTesting();

        List<Integer> verifiedIds = repo.findAllIds();
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

        setupForIdTesting();

        List<Integer> verifiedIds = repo.findAllIds();
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

    private void setupForIdTesting(){

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
    }








    // Tests for class DataPersistenceService

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










    // Tests for class DentistAppController

    @Test
    public void checkGetHome() throws Exception{

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists("appointments"))
                .andExpect(model().attributeExists("dentistNames"))
                .andExpect(model().attributeExists("searchResult"));
    }

    @Test
    public void checkGetEdit() throws Exception{
        mockForEditAndDelete("/edit", "form_edit");
    }

    @Test
    public void checkGetDelete() throws Exception{
        mockForEditAndDelete("/delete", "form_delete");
    }

    private void mockForEditAndDelete(String urlTemplate, String viewName) throws Exception{

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName))
                .andExpect(model().attributeExists("form"));
    }
}
