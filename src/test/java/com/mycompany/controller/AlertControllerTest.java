package com.mycompany.controller;

import com.mycompany.entity.Alert;
import com.mycompany.repository.AlertRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class AlertControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AlertRepository repository;

    @Before
    public void setup(){
        Alert alert = new Alert();
        alert.setVin("1HGCR2F3XFA027534");
        alert.setPriority("HIGH");
        alert.setDescription("asdf asd");
        alert.setTimestamp(new Date());
        alert.setLatitude(41.803194);
        alert.setLongitude(-88.144406);

        repository.save(alert);
    }

    @After
    public void cleanup(){
        repository.deleteAll();
    }

    @Test
    public void findAlerts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alerts/vin/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void findAlertsByPriority() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alerts/priority/HIGH"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }
}