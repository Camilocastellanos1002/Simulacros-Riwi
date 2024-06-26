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
public class SubmissionResp {
    
    private Long submission_id;

    private String context;

    private LocalDate submission_date;

    private Double grade;

    /*Relacion con el usuario */
    private UserBasicResp user; /* respondo con la info basica de user, sin demas listas */

    /*Relacion con la tarea */
    private AssignmentBasicResp assignment; /* respondo con la info basica de la tarea y trae la info de la leccion */
}
