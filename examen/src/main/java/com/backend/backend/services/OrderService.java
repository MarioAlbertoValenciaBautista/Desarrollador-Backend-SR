package com.backend.backend.services;

import com.backend.backend.models.OrderDTO;
import com.backend.backend.models.StatusEnum;
import com.backend.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class.getName());
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Create a new order or update an existing one.
     * Makes sure that the origin and destination are not null.
     *
     * @param orderDTO OrderDTO object with order data.
     * @return OrderDTO The order saved un DB.
     * @throws IllegalArgumentException Origin or destination null.
     * @throws RuntimeException If occour an exception while creating/updating orders.
     */
    public OrderDTO createOrder(OrderDTO orderDTO) {

        try {
            LOGGER.info("Creating order with: {}", orderDTO);
        if (orderDTO.getOrigin() == null || orderDTO.getDestination() == null) {
            LOGGER.error("Origin or destination is null");
            throw new IllegalArgumentException("Origin and destination cannot be null");
        }
        if (orderDTO.getId() != null) {
            orderDTO = updateOrderStatus(orderDTO);
        } else {
            orderDTO.setStatus(StatusEnum.CREATED.getStatus());
            orderDTO.setCreatedAt(java.time.LocalDateTime.now());
            orderDTO.setUpdatedAt(java.time.LocalDateTime.now());
        }
        LOGGER.info("Order saved succesfully: {}", orderDTO);
        return orderRepository.save(orderDTO);
        } catch (Exception e) {
            LOGGER.error("Error while creating order: {}", e.getMessage());
            throw new RuntimeException("Error creating order: " + e.getMessage());
        }
    }

    /**
     * Retrieves an order by its unique identifier.
     * Logs the retrieval process and errors.
     *
     * @param id Unique identifier of the order.
     * @return OrderDTO The found order, or null if not found.
     * @throws RuntimeException If an error occurs while retrieving the order.
     */
    public OrderDTO getOrder(Long id) {
        try {
            LOGGER.info("Retrieving order with id: {}", id);
            return orderRepository.findById(id).orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error while retrieving order with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error retrieving order: " + e.getMessage());
        }
    }

    /**
     * Lists orders filtered by status, origin, destination, and date range.
     * Validates that the start date is not after the end date.
     * Logs the filtering process and errors.
     *
     * @param status Status of the order.
     * @param origin Origin of the order.
     * @param destination Destination of the order.
     * @param starDate Start date of the range.
     * @param endDate End date of the range.
     * @return ArrayList<OrderDTO> List of orders matching the filters.
     * @throws IllegalArgumentException If the start date is after the end date.
     * @throws RuntimeException If an error occurs while listing the orders.
     */
    public ArrayList<OrderDTO> listOrders(String status, String origin, String destination, LocalDateTime starDate, LocalDateTime endDate) {
        try {
            LOGGER.info("Listing orders with filters - Status: {}, Origin: {}, Destination: {}, Start Date: {}, End Date: {}",
                        status, origin, destination, starDate, endDate);
        if (starDate != null && endDate != null && starDate.isAfter(endDate)) {
            LOGGER.error("Start date cannot be after end date");
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
            return (ArrayList<OrderDTO>) orderRepository.findByFilters(status, origin, destination, starDate, endDate);
        } catch (Exception e) {
            LOGGER.error("Error while listing orders: {}", e.getMessage());
            throw new RuntimeException("Error listing orders: " + e.getMessage());
        }
    }


    private OrderDTO updateOrderStatus(OrderDTO orderDTO) {
        LOGGER.info("Updating order status for order: {}", orderDTO);
        if (orderDTO.getId() == null) {
            LOGGER.error("Order ID cannot be null for status update");
            throw new IllegalArgumentException("Order ID cannot be null for status update");
        }
        OrderDTO auxOrderDTO = orderRepository.findById(orderDTO.getId()).orElse(null);
        if (auxOrderDTO == null) return null;

        if(StringUtils.hasText(orderDTO.getStatus()) && validateStatus(orderDTO.getStatus())){
            orderDTO.setStatus(changeStatus(orderDTO.getStatus(), auxOrderDTO.getStatus()));
        } else {
            throw  new IllegalArgumentException("Invalid status");
        }
        orderDTO.setCreatedAt(auxOrderDTO.getCreatedAt());
        orderDTO.setUpdatedAt(java.time.LocalDateTime.now());
        return orderDTO;
    }

    private boolean validateStatus(String status) {
        return  status.equals(StatusEnum.CREATED.getStatus()) ||
                status.equals(StatusEnum.IN_TRANSIT.getStatus()) ||
                status.equals(StatusEnum.DELIVERED.getStatus()) ||
                status.equals(StatusEnum.CANCELLED.getStatus());
    }

    private String changeStatus(String newStatus, String status) {
        String statusUpdated = null;
       if(status.equals(StatusEnum.CREATED.getStatus())){
           if(newStatus.equals(StatusEnum.IN_TRANSIT.getStatus()) || newStatus.equals(StatusEnum.CANCELLED.getStatus())){
               statusUpdated = newStatus;
           } else
               throw  new IllegalArgumentException("No se puede cambiar de estado a " + newStatus + " desde " + status);
       }else if(status.equals(StatusEnum.IN_TRANSIT.getStatus())){
                if(newStatus.equals(StatusEnum.DELIVERED.getStatus()) || newStatus.equals(StatusEnum.CANCELLED.getStatus())){
                     statusUpdated = newStatus;
                } else
                     throw  new IllegalArgumentException("No se puede cambiar de estado a " + newStatus + " desde " + status);
       }else {
           throw   new IllegalArgumentException("No se puede cambiar de un estado finalizado");
       }
       return statusUpdated;
    }

}
