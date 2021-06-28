package com.mycompany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mycompany.entity.Vehicle;
import com.mycompany.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository repository;

    @Before
    public void setup(){
        Vehicle vehicle = new Vehicle();
        vehicle.setVin("1HGCR2F3XFA027534");
        vehicle.setMake("HONDA");
        vehicle.setModel("ACCORD");
        vehicle.setYear(2015);
        vehicle.setRedlineRpm(5500);
        vehicle.setMaxFuelVolume(15);
        vehicle.setLastServiceDate(new Date());

        repository.save(vehicle);
    }

    @After
    public void cleanup(){
        repository.deleteAll();
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
           .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void findOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void addVehicles() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Vehicle vehicle = new Vehicle();
        vehicle.setVin("1HGCR2F3XFA027000");
        vehicle.setMake("HONDA");
        vehicle.setModel("ACCORD");
        vehicle.setYear(2016);
        vehicle.setRedlineRpm(5555);
        vehicle.setMaxFuelVolume(15);
        vehicle.setLastServiceDate(new Date());

        List<Vehicle> vehicles = Collections.singletonList(vehicle);

        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(vehicles))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027000")));


        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void delete() {
    }
}