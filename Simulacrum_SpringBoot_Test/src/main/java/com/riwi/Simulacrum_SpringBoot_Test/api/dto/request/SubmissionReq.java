package com.riwi.Simulacrum_SpringBoot_Test.api.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionReq {
    
    private String context;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "the date can't be in the past")
    private LocalDate submission_date;

    @DecimalMin(value = "0.0") /* Requerimiento decimal minimo */
    @DecimalMax(value = "5.0") /*Requerimiento decimal maximo */
    private Double grade;

    @NotBlank(message = "the id user is required")
    private Long id_user;

    @NotBlank(message = "the id assignment is required")
    private Long id_assignment;
}
