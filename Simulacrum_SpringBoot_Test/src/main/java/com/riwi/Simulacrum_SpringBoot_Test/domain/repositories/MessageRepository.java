package com.riwi.Simulacrum_SpringBoot_Test.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    
}
