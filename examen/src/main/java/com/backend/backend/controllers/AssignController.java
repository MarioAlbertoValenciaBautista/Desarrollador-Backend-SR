package com.backend.backend.controllers;

import com.backend.backend.models.AssignDTO;
import com.backend.backend.services.AssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assign")
public class AssignController {

    @Autowired
    private AssignService assignService;
    /**
     * Assign an order to driver
     *
     * @param driverId
     * @param orderId
     * @return DriverDTO The saved driver.
     */
    @PostMapping
    public AssignDTO assignDriver(@RequestParam (required = false) Long driverId,
                                  @RequestParam (required = false) Long orderId
    ){
        return assignService.assignDriverToOrder(driverId, orderId);

    }
}
