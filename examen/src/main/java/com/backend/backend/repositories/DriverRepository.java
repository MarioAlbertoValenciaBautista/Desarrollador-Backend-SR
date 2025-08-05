package com.backend.backend.repositories;

import com.backend.backend.models.DriverDTO;
import com.backend.backend.models.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Driver;
import java.util.Date;
import java.util.List;

@Repository
public interface DriverRepository extends CrudRepository<DriverDTO, Long>{

    // Custom query methods can be defined here if needed
    // For example, to find drivers by their status or location
    // List<DriverDTO> findByStatus(String status);
    // List<DriverDTO> findByLocation(String location);
    List<DriverDTO> findByActiveTrue();
}
