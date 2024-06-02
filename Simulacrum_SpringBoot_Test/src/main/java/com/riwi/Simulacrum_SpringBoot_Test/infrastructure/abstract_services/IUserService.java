package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.UserReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserResp;

public interface IUserService extends CRUDService<UserReq, UserResp,Long>{
    
    public final String FIELD_BY_SORT = "full_name"; /*Variable a utilizar para la paginacion */
}
