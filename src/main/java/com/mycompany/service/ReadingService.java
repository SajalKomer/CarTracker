package com.mycompany.service;

import com.mycompany.entity.Reading;

import java.util.List;

public interface ReadingService {
    Reading create(Reading reading);
    List<Reading> findAll();

    Reading findOne(String id);

}
