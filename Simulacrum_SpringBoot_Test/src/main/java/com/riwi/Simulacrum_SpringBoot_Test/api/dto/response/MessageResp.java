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
public class MessageResp {
    
    private Long message_id;

    private String message_context;

    private LocalDate send_date;

    /*Relacion con usuario */
        private UserBasicResp user_sender; /*  respondo con la info basica de usario como transmisor*/

        private UserBasicResp user_receiver; /* respondo con la info basica de usuario como receptor */

    /*Relacion con curso */
    private CourseBasicResp course; //??
}
