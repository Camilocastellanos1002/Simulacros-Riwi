package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.SubmissionReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.SubmissionResp;

public interface ISubmissionService extends CRUDService<SubmissionReq,SubmissionResp,Long>{
    
    public final String FIELD_BY_SORT = "submission_date"; /*Variable a utilizar para la paginacion */
}
