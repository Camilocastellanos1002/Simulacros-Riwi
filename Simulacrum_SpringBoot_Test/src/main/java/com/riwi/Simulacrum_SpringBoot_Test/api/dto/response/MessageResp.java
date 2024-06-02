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
public class MessageResp {
    
    private Long message_id;

    private String message_context;

    private LocalDate send_date;

    private Course course;

    private User user_sender;

    private User user_receiver;
}
