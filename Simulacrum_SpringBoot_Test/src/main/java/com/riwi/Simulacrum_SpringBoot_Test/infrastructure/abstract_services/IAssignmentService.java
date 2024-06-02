package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.AssignmentReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.AssignmentResp;

public interface IAssignmentService extends CRUDService <AssignmentReq,AssignmentResp,Long>{
    
    public final String FIELD_BY_SORT ="assignment_title"; /*Variable a utilizar para la paginacion */
}
