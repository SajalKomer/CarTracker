package com.mycompany.controller;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mycompany.entity.Reading;
import com.mycompany.entity.Tires;
import com.mycompany.repository.ReadingRepository;
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
import springfox.documentation.spring.web.json.Json;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class ReadingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReadingRepository repository;

    @Before
    public void setup(){
        Tires tires = new Tires();
        tires.setFrontLeft(34);
        tires.setFrontRight(36);
        tires.setRearLeft(29);
        tires.setRearRight(34);

        Reading reading = new Reading();
        reading.setVin("1HGCR2F3XFA027534");
        reading.setLatitude(41.803194);
        reading.setLongitude(-88.144406);
        reading.setTimestamp(new Date());
        reading.setFuelVolume(1.5);
        reading.setSpeed(85);
        reading.setEngineHp(240);
        reading.setCheckEngineLightOn(false);
        reading.setEngineCoolantLow(true);
        reading.setCruiseControlOn(true);
        reading.setEngineRpm(6300);
        reading.setTires(tires);

        repository.save(reading);
    }

    @After
    public void cleanup(){
        repository.deleteAll();
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/readings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Json json = new Json("{\n" +
                "   \"vin\": \"1HGCR2F3XFA027535\",\n" +
                "   \"latitude\": 41.803194,\n" +
                "   \"longitude\": -88.144406,\n" +
                "   \"timestamp\": \"2017-05-25T17:31:25.268Z\",\n" +
                "   \"fuelVolume\": 1.5,\n" +
                "   \"speed\": 85,\n" +
                "   \"engineHp\": 240,\n" +
                "   \"checkEngineLightOn\": false,\n" +
                "   \"engineCoolantLow\": true,\n" +
                "   \"cruiseControlOn\": true,\n" +
                "   \"engineRpm\": 6300,\n" +
                "   \"tires\": {\n" +
                "      \"frontLeft\": 34,\n" +
                "      \"frontRight\": 36,\n" +
                "      \"rearLeft\": 29,\n" +
                "      \"rearRight\": 34\n" +
                "   }\n" +
                "}");

        mvc.perform(MockMvcRequestBuilders.post("/readings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(json))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027535")));


        mvc.perform(MockMvcRequestBuilders.get("/readings"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }
}