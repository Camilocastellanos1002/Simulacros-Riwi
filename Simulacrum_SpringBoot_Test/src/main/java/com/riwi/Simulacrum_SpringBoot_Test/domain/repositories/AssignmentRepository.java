package com.riwi.Simulacrum_SpringBoot_Test.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    
}
