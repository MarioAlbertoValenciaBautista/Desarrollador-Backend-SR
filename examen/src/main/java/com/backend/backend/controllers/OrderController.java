package com.backend.backend.controllers;


import com.backend.backend.models.OrderDTO;
import com.backend.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<OrderDTO> listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String date
    ) {
        LocalDateTime starDate = null;
        LocalDateTime endDate = null;
        if(date != null){
            LocalDate localDate = LocalDate.parse(date);
            starDate = localDate.atStartOfDay();
            endDate = localDate.atTime(23, 59, 59);
        }
        return orderService.listOrders(status, origin, destination, starDate, endDate);
    }


}
