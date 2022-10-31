package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistAppointmentDTO;
import com.cgi.dentistapp.service.DentistAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistAppointmentDTO dentistAppointmentDTO, Model model){
        model.addAttribute("appointments", dentistAppointmentService.getAllAppointments());
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
        return "redirect:/results";
    }
}
