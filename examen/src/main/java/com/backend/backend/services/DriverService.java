package com.backend.backend.services;

import com.backend.backend.models.DriverDTO;
import com.backend.backend.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;


    public DriverDTO createDriver(DriverDTO driverDTO) {
        // Logic to create a driver
        if(driverDTO.getName() == null || driverDTO.getName().isEmpty()
                || driverDTO.getLicenseNumber() == null || driverDTO.getLicenseNumber().isEmpty()
                || driverDTO.getActive() == null) {
            return null;
        }
        return driverRepository.save(driverDTO);
    }

    public List<DriverDTO> listDrivers() {
        // Logic to list drivers based on status
        return (List<DriverDTO>) driverRepository.findByActiveTrue();
    }
}
