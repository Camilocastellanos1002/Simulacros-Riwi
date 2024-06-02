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
public class MessageReceiverResp {
    
    private Long message_id;

    private String message_context;

    private LocalDate send_date;

    private UserBasicResp user_receiver;
}
