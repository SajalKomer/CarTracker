package com.mycompany.service;

import com.mycompany.entity.Reading;
import com.mycompany.entity.Tires;
import com.mycompany.entity.Vehicle;
import com.mycompany.repository.AlertRepository;
import com.mycompany.repository.ReadingRepository;
import com.mycompany.repository.VehicleRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ReadingServiceImplTest {

    @TestConfiguration
    static class ReadingServiceImplTestConfiguration{
        @Bean
        public ReadingService getService() {
            return new ReadingServiceImpl();
        }
    }

    @Autowired
    private ReadingService service;

    @MockBean
    private ReadingRepository repository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private AlertRepository alertRepository;

    List<Reading> readings;

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

        readings = Collections.singletonList(reading);

        Mockito.when(repository.findAll())
                .thenReturn(readings);
        Mockito.when(repository.findById(reading.getId()))
                .thenReturn(Optional.of(reading));
        Mockito.when(vehicleRepository.findByVin(reading.getVin()))
                .thenReturn(Optional.of(new Vehicle()));
        Mockito.when(repository.save(Mockito.any(Reading.class)))
                .thenReturn(reading);
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Reading> result = service.findAll();
        Mockito.verify(repository).findAll();
        Assert.assertEquals("readings list should be matching", readings, result);
    }

    @Test
    public void testFindOne() {
        Reading result = service.findOne(readings.get(0).getId());
        Assert.assertEquals("reading should be matching", readings.get(0), result);
    }

    @Test
    public void testCreate() throws NoSuchFieldException {
        Reading result = service.create(readings.get(0));
        Assert.assertEquals("reading object should be matching", readings.get(0), result);
    }
}