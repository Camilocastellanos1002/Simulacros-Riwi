package com.riwi.Simulacrum_SpringBoot_Test.api.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentReq {
    @NotBlank(message = "the assigment title is required") /*mensaje del requerimiento, que no puede ser ni vacio ni null */
    @Size(max = 100,message = "the assignment title maximum lenght is 100 characters") /* requermiento de tamaño del titulo*/
    private String assignment_title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd") /*la fecha debe tener este formato */
    @FutureOrPresent(message = "the date can't be in the past") /* la fecha no puede ser antes a la creacion del registro */
    private LocalDate due_date;

    @NotBlank(message = "the id lesson is required")
    private Long id_lesson;
}


