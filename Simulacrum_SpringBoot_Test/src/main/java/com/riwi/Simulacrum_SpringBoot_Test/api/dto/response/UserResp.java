package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.util.List;
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

    private List<SubmissionBasicResp> submissions;/* respondo con la lista de  entregas (submission), del cual me trae infor de la tarea (assingment) del cual me trae info de leccion*/

    private List<CourseBasicResp> courses; /*respondo con la lista de cursos, con solo la informacion basica */

    private List<EnrollmentBasicResp> enrollments; /* respondo con la lista de inscripciones con info basica */

    private List<MessageSenderResp> messages_sender; /* respondo con la lista de mensajes como transmisor con info basica*/

    private List<MessageReceiverResp> messages_receiver; /*respondo con lista de mensajes como receptor con info basica*/



}
