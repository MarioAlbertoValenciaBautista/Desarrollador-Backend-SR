package com.backend.backend.repositories;

import com.backend.backend.models.AssignDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignRepository extends CrudRepository<AssignDTO, Long> { }
