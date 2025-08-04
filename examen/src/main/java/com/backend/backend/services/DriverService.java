package com.backend.backend.services;

import com.backend.backend.models.DriverDTO;
import com.backend.backend.repositories.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.util.List;

@Service
public class DriverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class);

    @Autowired
    DriverRepository driverRepository;

    /**
     * Creates a new driver if all required fields are present and valid.
     *
     * @param driverDTO DriverDTO object containing driver data.
     * @return DriverDTO The saved driver, or null if validation fails.
     */
    public DriverDTO createDriver(DriverDTO driverDTO) {
        try {
            LOGGER.info("Creating driver: {}", driverDTO);
            if (driverDTO.getName() == null || driverDTO.getName().isEmpty()
                    || driverDTO.getLicenseNumber() == null || driverDTO.getLicenseNumber().isEmpty()
                    || driverDTO.getActive() == null) {
                LOGGER.info("Driver creation failed due to missing fields: {}", driverDTO);
                return null;
            }
            LOGGER.info("Driver creation succeeded: {}", driverDTO);
            return driverRepository.save(driverDTO);
        } catch (Exception e) {
            LOGGER.info("Driver creation failed: {}", driverDTO);
            throw new RuntimeException("Error creating driver", e);
        }
    }

    /**
     * Lists all active drivers.
     *
     * @return List<DriverDTO> List of active drivers.
     */
    public List<DriverDTO> listDrivers() {
        try {
            LOGGER.info("Listing active drivers");
            return (List<DriverDTO>) driverRepository.findByActiveTrue();
        } catch (Exception e) {
            LOGGER.info("Driver listing failed");
            throw new RuntimeException("Error listing drivers", e);
        }
    }
}
