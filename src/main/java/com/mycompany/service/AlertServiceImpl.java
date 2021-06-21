package com.mycompany.service;

import com.mycompany.entity.Alert;
import com.mycompany.exception.AlertNotFoundException;
import com.mycompany.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AlertServiceImpl implements AlertService {
    @Autowired
    private AlertRepository repository;

//    @Autowired
//    public AlertServiceImpl(AlertRepository repository) {
//        this.repository = repository;
//    }

    @Transactional
    public List<Alert> findAlertsByPriority(String priority) {
        Iterable<Alert> existing = repository.findByPriority(priority);
        if(!existing.iterator().hasNext()) {
            throw new AlertNotFoundException("Alerts with Priority:- "+priority+" not found");
        }
        return (List<Alert>) existing;
    }

    @Transactional
    public List<Alert> findAlertsByVin(String vin) {
        Iterable<Alert> existing = repository.findByVin(vin);
        if(!existing.iterator().hasNext()) {
            throw new AlertNotFoundException("Alerts with VIN:- "+vin+" not found");
        }
        return (List<Alert>) existing;
    }

    @Transactional
    public Alert create(Alert alert) {
        return repository.save(alert);
    }
}
