package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.LessonReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonResp;

public interface ILessonService extends CRUDService<LessonReq,LessonResp,Long>{
    
    public final String FIELD_BY_SORT = "lesson_title"; /*Variable a utilizar para la paginacion */
}
