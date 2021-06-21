package com.mycompany.service;

import com.mycompany.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findOne(String id);

    List<Vehicle> addVehicles(List<Vehicle> vehicles);

    void delete(String id);
}
