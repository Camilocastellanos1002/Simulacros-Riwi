package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.time.LocalDate;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;

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

    private User user;

    private Course course;
}
