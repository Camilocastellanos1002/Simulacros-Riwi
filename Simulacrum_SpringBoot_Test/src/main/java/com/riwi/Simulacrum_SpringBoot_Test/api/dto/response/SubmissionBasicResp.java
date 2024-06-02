package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionBasicResp {
    
    private Long submission_id;

    private String context;

    private LocalDate submission_date;

    private Double grade;

    /* Por que debe tener un assignment basic resp ?  */
}
