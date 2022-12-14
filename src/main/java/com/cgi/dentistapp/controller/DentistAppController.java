package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.dto.AppointmentListWrapperDTO;
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
        modelSetupForHomeAndSearch(model, new AppointmentListWrapperDTO());
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistAppointmentDTO dentistAppointmentDTO, SearchDTO searchDTO, BindingResult bindingResult, Model model) {

        modelSetupForHomeAndSearch(model, new AppointmentListWrapperDTO());

        if (bindingResult.hasErrors()) {
            return "form";
        }

        if(!verificationService.checkIfAppointmentIsAvailable(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentDateTime())){
            String errorDateTimeBooked = "This date and time has already been booked for this dentist! Please check Appointment Table to determine available spots.";
            model.addAttribute("errorDateTimeBooked", errorDateTimeBooked);

            return "form";
        }

        dataPersistenceService.addAppointment(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentDateTime());
        return "redirect:/successful_registration";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model){
        modelSetupForEditAndDelete(model);
        return "form_edit";
    }

    @PostMapping("/edit")
    public String postEditForm(@ModelAttribute @Valid AppointmentListWrapperDTO form, BindingResult bindingResult, Model model){

        modelSetupForEditAndDelete(model);

        if(bindingResult.hasErrors()){
            String errorNonValidDentistOrDate = "Please enter a valid dentist name and a valid date!";
            model.addAttribute("errorNonValidDentistOrDate", errorNonValidDentistOrDate);

            return "form_edit";
        }

        if(!verificationService.checkIfAppointmentListIsValid(form.getAppointments())){
            String errorTwoEqualAppointments = "There can not be two equal appointments!";
            model.addAttribute("errorTwoEqualAppointments", errorTwoEqualAppointments);

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
            String errorNoChosenAppointments = "You did not choose any appointments!";
            model.addAttribute("errorNoChosenAppointments", errorNoChosenAppointments);

            return "form_delete";
        }

        dataPersistenceService.deleteAppointments(appointmentIds);
        return "redirect:/successful_delete";
    }

    @PostMapping("/search")
    public String postSearchForm(@Valid SearchDTO searchDTO, DentistAppointmentDTO dentistAppointmentDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            modelSetupForHomeAndSearch(model, new AppointmentListWrapperDTO());
            return "form";
        }

        List<DentistAppointmentDTO> searchResultList = dataPersistenceService.getAppointmentDTOsBySearch(searchDTO.getDentistName(), searchDTO.getStartingFromDate(), searchDTO.getEndOnDate());
        AppointmentListWrapperDTO searchResultWrapperDTO = new AppointmentListWrapperDTO(searchResultList);

        modelSetupForHomeAndSearch(model, searchResultWrapperDTO);
        return "form";
    }

    private void modelSetupForHomeAndSearch(Model model, AppointmentListWrapperDTO searchResultWrapperDTO){
        AppointmentListWrapperDTO appointmentsWrapperDTO = new AppointmentListWrapperDTO(dataPersistenceService.getAllAppointmentsAsDTO());
        List<String> dentistNames = DentistNames.getListOfDentists();

        model.addAttribute("appointments", appointmentsWrapperDTO);
        model.addAttribute("dentistNames", dentistNames);
        model.addAttribute("searchResult", searchResultWrapperDTO);
    }

    private void modelSetupForEditAndDelete(Model model){
        AppointmentListWrapperDTO wrapperDTO = new AppointmentListWrapperDTO(dataPersistenceService.getAllAppointmentsAsDTO());
        model.addAttribute("form", wrapperDTO);
    }
}
