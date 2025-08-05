package com.backend.backend.services;


import com.backend.backend.models.AssignDTO;
import com.backend.backend.models.DriverDTO;
import com.backend.backend.models.OrderDTO;
import com.backend.backend.models.StatusEnum;
import com.backend.backend.repositories.AssignRepository;
import com.backend.backend.repositories.DriverRepository;
import com.backend.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignService {

    @Autowired
    private AssignRepository assignRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DriverRepository driverRepository;

    public AssignDTO assignDriverToOrder(Long driverId, Long orderId) {
        // Example of setting a driver
        try {
            OrderDTO orderDTO = orderRepository.findById(orderId).orElse(null);
            DriverDTO driverDTO = driverRepository.findById(driverId).orElse(null);


            AssignDTO assignDTO = null;
            if (orderDTO == null || driverDTO == null) {
                throw new IllegalArgumentException("Order or Driver not found");
            } else {
                if (driverDTO.getActive() != null && driverDTO.getActive()) {
                    if (orderDTO.getStatus() != null && orderDTO.getStatus().equals(StatusEnum.CREATED.getStatus())) {
                        assignDTO = new AssignDTO();
                        assignDTO.setDriverId(driverDTO.getId());
                        assignDTO.setOrderId(orderDTO.getId());
                        assignDTO.setDriverName(driverDTO.getName().isEmpty() ? "DRIVER NOT FOUND" :driverDTO.getName());
                    } else {
                        throw new IllegalArgumentException("Order is not in a valid state for assignment");
                     }
                }
            }
            return assignRepository.save(assignDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving order or driver: " + e.getMessage());
        }
    }
}
