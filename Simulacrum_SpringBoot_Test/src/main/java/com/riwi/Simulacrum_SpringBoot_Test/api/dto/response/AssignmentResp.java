package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Lesson;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResp {
    
    private Long assignment_id;

    private String assignment_title;

    private String description;

    private LocalDate due_date;

    private Lesson lesson;

    private List<Submission> submissions;
}
