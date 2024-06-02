package com.riwi.Simulacrum_SpringBoot_Test.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
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

@Entity(name = "submissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submission_id;

    private String context;

    private LocalDate submission_date;

    @Column(length = 5,nullable = false)
    private Double grade;

    /*Relacion con usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id_user") /*forma de linkear id del usuario */
    private User user;

    /* Relacion con assignment */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id",referencedColumnName = "id_assignment")
    private Assignment assignment;

}
