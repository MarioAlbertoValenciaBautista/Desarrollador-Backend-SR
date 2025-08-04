package com.backend.backend.services;

import com.backend.backend.models.OrderDTO;
import com.backend.backend.models.StatusEnum;
import com.backend.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    // Method to create a new order
    // It takes an OrderDTO object as input and returns the saved OrderDTO object
    // The method uses the orderRepository to save the order to the database
    // It is annotated with @Service to indicate that it is a service component in the Spring
    // framework, which allows it to be injected into other components like controllers.
    // The method returns an ArrayList of OrderDTO objects, which represents all the orders in
    // the database. This method is typically used to retrieve all orders for display in a user
    // interface or for further processing in the application.
    public OrderDTO createOrder(OrderDTO orderDTO) {
        if(orderDTO.getOrigin() == null || orderDTO.getDestination() == null) {
            throw new IllegalArgumentException("Origin and destination cannot be null");
        }
        if(orderDTO.getId() != null) {
            orderDTO = updateOrderStatus(orderDTO);
        } else {
            orderDTO.setStatus(StatusEnum.CREATED.getStatus());
            orderDTO.setCreatedAt(java.time.LocalDateTime.now());
            orderDTO.setUpdatedAt(java.time.LocalDateTime.now());
        }
        return orderRepository.save(orderDTO);
    }

    public OrderDTO getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public ArrayList<OrderDTO> listOrders(String status, String origin, String destination, LocalDateTime starDate, LocalDateTime endDate) {
        return (ArrayList<OrderDTO>) orderRepository.findByFilters(status, origin, destination, starDate, endDate);
    }


    private OrderDTO updateOrderStatus(OrderDTO orderDTO) {
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
