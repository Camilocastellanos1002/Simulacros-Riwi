package com.riwi.Simulacrum_SpringBoot_Test.domain.entities;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "lessons")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lesson_id;
    
    @Column(length = 100, nullable = false)
    private String lesson_title;

    private String context;

    /*Relacion con course */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",referencedColumnName = "course_id")
    private Course course;

    /*Relacion con Assignment */
    @OneToMany(mappedBy = "lesson",fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List <Assignment> assignments;

}

