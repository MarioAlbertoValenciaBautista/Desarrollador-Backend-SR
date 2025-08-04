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

    /**
     * Creates a new order using the provided OrderDTO.
     *
     * @param orderDTO OrderDTO object containing order data.
     * @return OrderDTO The saved order.
     */
    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id Unique identifier of the order.
     * @return OrderDTO The found order, or null if not found.
     */
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    /**
     * Lists orders filtered by status, origin, destination, and date.
     * If a date is provided, filters orders for that specific day.
     *
     * @param status Status of the order (optional).
     * @param origin Origin of the order (optional).
     * @param destination Destination of the order (optional).
     * @param date Date to filter orders (optional, format: yyyy-MM-dd).
     * @return List<OrderDTO> List of orders matching the filters.
     */
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
