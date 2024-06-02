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
public class AssignmentBasicResp {
    
    private Long assignment_id;

    private String assignment_title;

    private String description;

    private LocalDate due_date;

    /* por que debe tener lesson basic resp ? */
}
