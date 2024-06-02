package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.util.List;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Enrollment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Lesson;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Message;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResp {
    
    private Long course_id;

    private String course_name;

    private String description;

    private User user;
    
    private List<Lesson> lessons;

    private List<Enrollment> elEnrollments;
    
    private List<Message> messages;
}
