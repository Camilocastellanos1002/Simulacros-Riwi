package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.util.List;

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

    /*Relacion con user */
    private UserBasicResp user; /*respondo con la info basica del usuario */
    
    /*Relacion con la leccion */
    private List<LessonBasicResp> lessons; /*respondo con la info basica de la leccion */

    /*Relacion con la inscripcion */
    private List<EnrollmentBasicResp> elEnrollments; /*Respondo con la informacion basica de la inscripcion */ 
    //??
    
    /*Relacion con la table mensaje */
    private List<MessageCourseResp> messages; /* respondo con la info basica del mensaje, que contiene la info basica del transmisor y el receptor */
}
