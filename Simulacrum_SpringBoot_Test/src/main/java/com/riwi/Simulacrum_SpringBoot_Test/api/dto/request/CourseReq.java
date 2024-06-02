package com.riwi.Simulacrum_SpringBoot_Test.api.dto.request;

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
public class CourseReq {

    @NotBlank(message = "the course name is required")
    @Size(max = 100,message = "the course name maximum lenght is 100 characters")
    private String course_name;

    /*no es obligatorio la descripcion segun la tabla */
    private String description;

    @NotBlank(message = "the id user is required")
    private Long id_user;
}
