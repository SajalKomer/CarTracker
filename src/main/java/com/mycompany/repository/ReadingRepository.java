package com.mycompany.repository;

import com.mycompany.entity.Reading;
import org.springframework.data.repository.CrudRepository;

public interface ReadingRepository extends CrudRepository<Reading, String> {
}
