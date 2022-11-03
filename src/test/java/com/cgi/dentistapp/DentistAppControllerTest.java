package com.cgi.dentistapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// got a lot of this from https://rieckpil.de/test-thymeleaf-controller-endpoints-with-spring-boot-and-mockmvc/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class DentistAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
