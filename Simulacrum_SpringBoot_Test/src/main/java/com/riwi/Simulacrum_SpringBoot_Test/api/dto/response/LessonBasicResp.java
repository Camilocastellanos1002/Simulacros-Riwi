package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonBasicResp {
    
    private Long lesson_id;

    private String lesson_title;

    private String context;
}
