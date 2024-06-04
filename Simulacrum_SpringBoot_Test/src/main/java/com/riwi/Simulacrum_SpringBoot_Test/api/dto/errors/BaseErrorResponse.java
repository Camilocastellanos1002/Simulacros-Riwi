package com.riwi.Simulacrum_SpringBoot_Test.api.dto.errors;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder //esta clase sera heredada por otra
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorResponse implements Serializable {  //Serializable permite responder errores con metodos HTTP

    //El error respondera con el codigo http y un string de descripcion del error
    private Integer code;
    private String status; 
}
