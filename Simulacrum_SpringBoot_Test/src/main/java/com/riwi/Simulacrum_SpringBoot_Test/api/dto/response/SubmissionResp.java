package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.time.LocalDate;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Assignment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResp {
    
    private Long submission_id;

    private String context;

    private LocalDate submission_date;

    private Double grade;

    private User user;

    private Assignment assignment;
}
