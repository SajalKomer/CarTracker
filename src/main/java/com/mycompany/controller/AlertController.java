package com.mycompany.controller;

import com.mycompany.entity.Alert;
import com.mycompany.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {
    @Autowired
    private AlertService service;

//    @Autowired
//    public AlertController(AlertService alertService) {
//        this.service = alertService;
//    }

    @GetMapping(path = "/vin/{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @RequestMapping(method = RequestMethod.GET, value = "/vin/{vin}",
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Alert> findAlerts(@PathVariable String vin) {
        return service.findAlertsByVin(vin);
    }

    @GetMapping(path = "/priority/{priority_type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @RequestMapping(method = RequestMethod.GET, value = "/priority/{priority_type}",
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Alert> findAlertsByPriority(@PathVariable String priority_type) {
        return service.findAlertsByPriority(priority_type);
    }
}
