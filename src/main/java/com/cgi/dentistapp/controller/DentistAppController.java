package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.dto.WrapperForListOfAppointmentsDTO;
import com.cgi.dentistapp.dto.SearchDTO;
import com.cgi.dentistapp.enums.DentistNames;
import com.cgi.dentistapp.service.DataPersistenceService;
import com.cgi.dentistapp.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DataPersistenceService dataPersistenceService;
    @Autowired
    private VerificationService verificationService;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/successful_registration").setViewName("successful_registration");
        registry.addViewController("/successful_edit").setViewName("successful_edit");
        registry.addViewController("/successful_delete").setViewName("successful_delete");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistAppointmentDTO dentistAppointmentDTO, SearchDTO searchDTO, Model model){
        modelSetupForHomeAndSearch(model, new WrapperForListOfAppointmentsDTO());
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistAppointmentDTO dentistAppointmentDTO, SearchDTO searchDTO, BindingResult bindingResult, Model model) {

        modelSetupForHomeAndSearch(model, new WrapperForListOfAppointmentsDTO());

        if (bindingResult.hasErrors()) {
            return "form";
        }

        if(!verificationService.checkIfAppointmentIsAvailable(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentTime())){
            String error = "This date and time has already been booked for this dentist! Please check Appointment Table to determine available spots.";
            model.addAttribute("error", error);

            return "form";
        }

        dataPersistenceService.addAppointment(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentTime());
        return "redirect:/successful_registration";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model){
        modelSetupForEditAndDelete(model);
        return "form_edit";
    }

    @PostMapping("/edit")
    public String postEditForm(@ModelAttribute @Valid WrapperForListOfAppointmentsDTO form, BindingResult bindingResult, Model model){

        modelSetupForEditAndDelete(model);

        if(bindingResult.hasErrors()){
            String error = "Please enter a valid dentist name and a valid date!";
            model.addAttribute("error", error);

            return "form_edit";
        }

        if(!verificationService.checkIfAppointmentListIsAvailable(form.getAppointments())){
            String twoEqualAppointments = "There can not be two equal appointments!";
            model.addAttribute("twoEqualAppointments", twoEqualAppointments);

            return "form_edit";
        }


        dataPersistenceService.editAppointments(form.getAppointments());
        return "redirect:/successful_edit";
    }

    @GetMapping("/delete")
    public String showDeleteForm(Model model){
        modelSetupForEditAndDelete(model);
        return "form_delete";
    }

    @PostMapping("/delete")
    public String postDeleteForm(@RequestParam(required = false) String appointmentIds, Model model){

        modelSetupForEditAndDelete(model);

        if((appointmentIds == null) || (appointmentIds.isEmpty()) || (!verificationService.checkIfIdsAreValid(appointmentIds))){
            String error = "You did not choose any appointments!";
            model.addAttribute("error", error);

            return "form_delete";
        }

        dataPersistenceService.deleteAppointments(appointmentIds);
        return "redirect:/successful_delete";
    }

    @PostMapping("/search")
    public String postSearchForm(@Valid SearchDTO searchDTO, DentistAppointmentDTO dentistAppointmentDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            modelSetupForHomeAndSearch(model, new WrapperForListOfAppointmentsDTO());
            return "form";
        }

        List<DentistAppointmentDTO> dtoList = dataPersistenceService.getAppointmentsBySearch(searchDTO.getDentistName(), searchDTO.getStartingFromDate(), searchDTO.getEndOnDate());
        WrapperForListOfAppointmentsDTO searchResultWrapperDTO = new WrapperForListOfAppointmentsDTO(dtoList);

        modelSetupForHomeAndSearch(model, searchResultWrapperDTO);
        return "form";
    }

    private void modelSetupForHomeAndSearch(Model model, WrapperForListOfAppointmentsDTO searchResultWrapperDTO){
        WrapperForListOfAppointmentsDTO appointmentsWrapperDTO = new WrapperForListOfAppointmentsDTO(dataPersistenceService.getAllAppointmentsAsDTO());
        List<String> dentistNames = DentistNames.getListOfDentists();

        model.addAttribute("appointments", appointmentsWrapperDTO);
        model.addAttribute("dentistNames", dentistNames);
        model.addAttribute("searchResult", searchResultWrapperDTO);
    }

    private void modelSetupForEditAndDelete(Model model){
        WrapperForListOfAppointmentsDTO wrapperDTO = new WrapperForListOfAppointmentsDTO(dataPersistenceService.getAllAppointmentsAsDTO());
        model.addAttribute("form", wrapperDTO);
    }
}
