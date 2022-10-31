package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.dto.ListOfDentistAppointmentsDTO;
import com.cgi.dentistapp.service.DentistAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistAppointmentService dentistAppointmentService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/successful_registration").setViewName("successful_registration");
        registry.addViewController("/successful_edit").setViewName("successful_edit");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistAppointmentDTO dentistAppointmentDTO, Model model){
        ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(dentistAppointmentService.getAllAppointmentsAsDTO());

        model.addAttribute("appointments", appointmentListDTO);
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistAppointmentDTO dentistAppointmentDTO, BindingResult bindingResult) {


        System.out.println("DATA: -" + dentistAppointmentDTO.getAppointmentTime() + "-");

        if (bindingResult.hasErrors()) {
            return "form";
        }

        System.out.println("GOT HERE");
        System.out.println(dentistAppointmentDTO.getDentistName());
        System.out.println(dentistAppointmentDTO.getAppointmentTime());

        dentistAppointmentService.addAppointment(dentistAppointmentDTO.getDentistName(), dentistAppointmentDTO.getAppointmentTime());
        return "redirect:/successful_registration";
    }

    @GetMapping("/edit")
    public String showEditForm(DentistAppointmentDTO dentistAppointmentDTO, Model model){
        ListOfDentistAppointmentsDTO appointmentListDTO = new ListOfDentistAppointmentsDTO(dentistAppointmentService.getAllAppointmentsAsDTO());

        model.addAttribute("form", appointmentListDTO);
        return "form_edit";
    }

    @PostMapping("/edit")
    public String postEditForm(@ModelAttribute ListOfDentistAppointmentsDTO form){

        for(DentistAppointmentDTO dto : form.getAppointments()){
            System.out.println(dto.getId() + " " + dto.getDentistName() + " " + dto.getAppointmentTime());
        }

        dentistAppointmentService.editAppointments();

        return "redirect:/successful_edit";
    }


}
