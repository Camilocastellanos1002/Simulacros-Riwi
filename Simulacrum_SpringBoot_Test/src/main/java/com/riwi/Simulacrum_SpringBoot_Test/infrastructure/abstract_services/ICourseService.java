package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.CourseReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.CourseResp;

public interface ICourseService extends CRUDService<CourseReq,CourseResp,Long>{
   
    public final String FIELD_BY_SORT = "course_name"; /*Constante publica de tipo String */
}
