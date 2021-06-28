package com.mycompany.service;

import com.mycompany.entity.Vehicle;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class VehicleServiceImplTest {

    @TestConfiguration
    static class VehicleServiceImplTestConfiguration{
        @Bean
        public VehicleService getService() {
            return new VehicleServiceImpl();
        }
    }

    @Autowired
    private VehicleService service;

    @MockBean
    private VehicleRepository repository;

    List<Vehicle> vehicles;

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

        vehicles = Collections.singletonList(vehicle);

        Mockito.when(repository.findAll())
                .thenReturn(vehicles);
        Mockito.when(repository.findByVin(vehicle.getVin()))
                .thenReturn(Optional.of(vehicle));
        Mockito.when(repository.saveAll(Mockito.anyList()))
                .thenReturn(vehicles);
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Vehicle> result = service.findAll();
        Mockito.verify(repository).findAll();
        Assert.assertEquals("vehicle list should be matching", vehicles, result);
    }

    @Test
    public void testFindOne() {
        Vehicle result = service.findOne(vehicles.get(0).getVin());
        Assert.assertEquals("vehicle object should be matching", vehicles.get(0), result);
    }

    @Test
    public void addVehicles() {
        List<Vehicle> result = service.addVehicles(vehicles);
        Assert.assertEquals("vehicle list should be matching", vehicles, result);
    }

    @Test
    public void delete() {
    }
}