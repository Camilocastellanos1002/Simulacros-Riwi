package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.util.List;

import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Enrollment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Message;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Submission;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResp {
    
    private Long user_id;

    private String user_name;

    private String password;

    private String email;

    private String full_name;

    private Rol role;

    private List<Submission> submissions;

    private List<Course> courses;

    private List<Enrollment> enrollments;

    private List<Message> messages_sender;

    private List<Message> messages_receiver;



}
