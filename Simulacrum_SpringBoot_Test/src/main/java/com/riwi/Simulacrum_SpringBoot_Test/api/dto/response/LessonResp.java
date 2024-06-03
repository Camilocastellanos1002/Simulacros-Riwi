package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResp {
    
    private Long lesson_id;

    private String lesson_title;

    private String context;

    /*Relacion con curso */
    private CourseBasicResp course; /*Respondo con la info basica del curso */

    /* Relacion con la tarea 
     * no se usa AssignmentBasicResp por que contiene a lessonbasic y genera bucle
    */
    private List <AssignmentLessonResp> assignments; /*respondo con la informacion basica de la tarea y info basica de leccion  */ //?

}
