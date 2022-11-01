package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.dto.ListOfDentistAppointmentsDTO;
import com.cgi.dentistapp.enums.ListOfDentists;
import com.cgi.dentistapp.service.DentistAppointmentService;
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
    private DentistAppointmentService service;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/successful_registration").setViewName("successful_registration");
        registry.addViewController("/successful_edit").setViewName("successful_edit");
        registry.addViewController("/successful_delete").setViewName("successful_delete");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistAppointmentDTO dentistAppointmentDTO, Model model){
        ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(service.getAllAppointmentsAsDTO());
        List<String> listOfDentists = ListOfDentists.getListOfDentists();

        model.addAttribute("appointments", appointmentListDTO);
        model.addAttribute("listOfDentists", listOfDentists);
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistAppointmentDTO dentistAppointmentDTO, BindingResult bindingResult, Model model) {

        // how does this redirect work without giving it 'appointments' into the model??
        if (bindingResult.hasErrors()) {
            return "form";
        }

        if(!service.checkIfAppointmentIsAvailable(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentTime())){
            String error = "This date and time has already been booked for this dentist! Please check Appointment Table to determine available spots.";
            model.addAttribute("error", error);

            ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(service.getAllAppointmentsAsDTO());
            List<String> listOfDentists = ListOfDentists.getListOfDentists();

            model.addAttribute("appointments", appointmentListDTO);
            model.addAttribute("listOfDentists", listOfDentists);

            return "form";
        }

        service.addAppointment(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentTime());
        return "redirect:/successful_registration";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model){
        ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(service.getAllAppointmentsAsDTO());

        model.addAttribute("form", appointmentListDTO);
        return "form_edit";
    }

    @PostMapping("/edit")
    public String postEditForm(@ModelAttribute @Valid ListOfDentistAppointmentsDTO form, BindingResult bindingResult, Model model){

        ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(service.getAllAppointmentsAsDTO());
        model.addAttribute("form", appointmentListDTO);

        if(bindingResult.hasErrors()){
            String error = "Please enter a valid dentist name and a valid date!";
            model.addAttribute("error", error);

            return "form_edit";
        }

        if(!service.checkIfAppointmentListIsAvailable(form.getAppointments())){
            String twoEqualAppointments = "There can not be two equal appointments!";
            model.addAttribute("twoEqualAppointments", twoEqualAppointments);

            return "form_edit";
        }


        service.editAppointments(form.getAppointments());
        return "redirect:/successful_edit";
    }

    @GetMapping("/delete")
    public String showDeleteForm(Model model){
        ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(service.getAllAppointmentsAsDTO());

        model.addAttribute("form", appointmentListDTO);
        return "form_delete";
    }

    @PostMapping("/delete")
    public String postDeleteForm(@RequestParam(required = false) String appointmentIds, Model model){

        if((appointmentIds == null) || (!service.checkIfIdsAreValid(appointmentIds))){

            ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(service.getAllAppointmentsAsDTO());
            model.addAttribute("form", appointmentListDTO);

            String error = "You did not choose any appointments!";
            model.addAttribute("error", error);

            return "form_delete";
        }

        service.deleteAppointments(appointmentIds);
        return "redirect:/successful_delete";
    }


}
