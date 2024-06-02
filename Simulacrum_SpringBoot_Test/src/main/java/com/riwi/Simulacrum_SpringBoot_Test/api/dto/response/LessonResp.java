package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.util.List;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Assignment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResp {
    
    private Long lesson_id;

    private String lesson_title;

    private String context;

    private Course course;

    private List <Assignment> assignments;

}
