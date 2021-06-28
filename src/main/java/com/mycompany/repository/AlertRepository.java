package com.mycompany.repository;

import com.mycompany.entity.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlertRepository extends CrudRepository<Alert, Integer> {

    Iterable<Alert> findByVin(String vin);

    Iterable<Alert> findByPriority(String priority);
}
