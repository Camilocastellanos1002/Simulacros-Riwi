package com.riwi.Simulacrum_SpringBoot_Test.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "messages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    private String message_context;

    private LocalDate sent_date;

    /*Relacion con course */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",referencedColumnName = "course_id")
    private Course course;

    /*Relacion con user como Tx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender",referencedColumnName = "user_id")
    private User sender;

    /*Relacion con user como Rx */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver",referencedColumnName = "user_id")
    private User receiver;

}
