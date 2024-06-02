package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseBasicResp {
    
    private Long course_id;

    private String course_name;

    private String description;
}
