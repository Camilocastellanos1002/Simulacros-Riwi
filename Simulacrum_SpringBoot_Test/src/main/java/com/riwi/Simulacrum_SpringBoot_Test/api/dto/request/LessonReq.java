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
public class LessonReq {

    @NotBlank(message = "the title lesson is required")
    @Size(max = 100, message = "the title lesson maximum length is 100 characters")
    private String lesson_title;

    private String context;

    @NotBlank(message = "the id course is required")
    private Long id_course;
}
