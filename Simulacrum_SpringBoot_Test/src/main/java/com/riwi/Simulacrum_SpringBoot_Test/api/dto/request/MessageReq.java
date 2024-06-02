package com.riwi.Simulacrum_SpringBoot_Test.api.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageReq {
    
    @NotBlank(message="the context message is required")
    private String message_context;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "the date can't be in the past")
    private LocalDate sent_date;

    @NotBlank(message = "the user id sender user is required")
    private Long id_sender;

    @NotBlank(message = "the user id receiver is required")
    private Long id_receiver;

    @NotBlank(message = "the id course is required")
    private Long id_course;



}
