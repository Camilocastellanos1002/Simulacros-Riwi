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
public class EnrollmentResp {
    
    private Long enrollment_id;

    private LocalDate enrollment_date;

    private UserBasicResp user; /*Solo deseo la informacion basica del usuario */

    private CourseBasicResp course; /*Solo deseo la info basica del curso */
}
