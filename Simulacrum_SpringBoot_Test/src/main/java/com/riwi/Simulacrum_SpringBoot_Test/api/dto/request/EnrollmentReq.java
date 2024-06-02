package com.riwi.Simulacrum_SpringBoot_Test.api.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
public class EnrollmentReq {

    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "the date can't be in the past") /* la fecha no puede ser antes a la creacion del registro */
    private LocalDate enrollment_date;

    @NotBlank(message = "the id user is required")
    private Long id_user;

    @NotBlank(message = "the id course is required")
    private Long id_course;
}
