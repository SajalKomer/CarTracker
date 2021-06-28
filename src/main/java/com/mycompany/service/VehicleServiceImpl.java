package com.mycompany.service;

import com.mycompany.entity.Vehicle;
import com.mycompany.exception.BadRequestException;
import com.mycompany.exception.ResourceNotFoundException;
import com.mycompany.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;


    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return (List<Vehicle>) repository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehicle findOne(String vin) {
        Optional<Vehicle> existing = repository.findByVin(vin);
        if (!existing.isPresent()) {
            throw new ResourceNotFoundException("Vehicle with VIN " + vin + " doesn't exist.");
        }
        return existing.get();
    }

    @Transactional
    public List<Vehicle> addVehicles(List<Vehicle> vehicles) {
        try{
            return (List<Vehicle>) repository.saveAll(vehicles);
        }catch (Exception e){
            throw new BadRequestException("An error occurred! Could not add vehicles data. Check if the data is in proper format.");
        }
    }


    @Transactional
    public void delete(String vin) {
        Optional<Vehicle> existing = repository.findByVin(vin);
        if (!existing.isPresent()) {
            throw new ResourceNotFoundException("Employee with vin " + vin + " doesn't exist.");
        }
        repository.delete(existing.get());
    }
}
