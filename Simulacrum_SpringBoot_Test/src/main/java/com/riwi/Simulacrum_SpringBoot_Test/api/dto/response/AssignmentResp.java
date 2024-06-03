package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResp {
    
    private Long assignment_id;

    private String assignment_title;

    private String description;

    private LocalDate due_date;

    /*Relacion con la leccion */
    private LessonBasicResp lesson; /*Respondo con la info basica de la lesson */

    /*Relacion con la entrega */
    /*
     *  no se implementa SubmissionBasicResp por que se genera bucle ya que contiene, asignment y asignment tiene lesson
     * y/o por que no se borra lessonbasic?
     */
    private List<SubmissionAssingmentResp> submissions; /*Respondo con la info basica de la  entrega  */
}
