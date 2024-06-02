package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services;

import org.springframework.data.domain.Page;

import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;

public interface CRUDService <RQ,RS,ID>{
 
    public RS create(RQ request);

    public Page<RS> getAll(int page, int size, SortType sortType); /*Obtener todos los datos por paginacion */
    public RS getById(ID id);

    public RS update(RQ request, ID id);

    public void delete(ID id);

}
