package com.backend.backend.controllers;

import com.backend.backend.models.DriverDTO;
import com.backend.backend.repositories.DriverRepository;
import com.backend.backend.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * Creates a new driver using the provided DriverDTO.
     *
     * @param driverDTO DriverDTO object containing driver data.
     * @return DriverDTO The saved driver.
     */
    @PostMapping
    public DriverDTO createDriver(@RequestBody DriverDTO driverDTO) {
        return driverService.createDriver(driverDTO);
    }

    /**
     * Retrieves a list of all active drivers.
     *
     * @return List<DriverDTO> List of active drivers.
     */
    @GetMapping("/active")
    public List<DriverDTO> listDrivers() {
        return driverService.listDrivers();
    }
}
