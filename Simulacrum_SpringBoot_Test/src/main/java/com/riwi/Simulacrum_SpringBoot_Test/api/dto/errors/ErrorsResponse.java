package com.riwi.Simulacrum_SpringBoot_Test.api.dto.errors;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder //se llama el constructor del padre
@EqualsAndHashCode(callSuper = true) // Para que el padre sea el unico que ponga el serial, no genere dos espacios de memoria
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsResponse  extends BaseErrorResponse{
    private List<Map<String,String>> errors; // Lista de map u objetos de errores
}
