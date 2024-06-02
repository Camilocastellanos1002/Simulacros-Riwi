package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.MessageReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.MessageResp;

public interface IMessageService extends CRUDService <MessageReq,MessageResp,Long> {
    
    public final String FIELD_BY_SORT = "sent_date"; /*Variable a utilizar para la paginacion */
}
