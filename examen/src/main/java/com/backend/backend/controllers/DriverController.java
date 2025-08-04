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

    @PostMapping
    public DriverDTO createDriver(@RequestBody DriverDTO driverDTO) {
        return driverService.createDriver(driverDTO);
    }

    @GetMapping("/active")
    public List<DriverDTO> listDrivers() {
        return driverService.listDrivers();
    }
}
