package com.backend.backend.repositories;


import com.backend.backend.models.OrderDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderDTO,Long>{
    @Query("SELECT o FROM OrderDTO o WHERE " +
            "(:status IS NULL OR o.status = :status) AND " +
            "(:date IS NULL OR o.createdAt = :date) AND " +
            "(:origin IS NULL OR o.origin = :origin) AND " +
            "(:destination IS NULL OR o.destination = :destination)")
    List<OrderDTO> findByFilters(String status, String origin, String destination, LocalDateTime date);
}
