package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.EnrollmentReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.EnrollmentResp;

public interface IEnrollmentService extends CRUDService<EnrollmentReq,EnrollmentResp, Long> {
    
    public final String FIELD_BY_SORT = "enrollment_date"; /*Variable a utilizar para la paginacion */
}
