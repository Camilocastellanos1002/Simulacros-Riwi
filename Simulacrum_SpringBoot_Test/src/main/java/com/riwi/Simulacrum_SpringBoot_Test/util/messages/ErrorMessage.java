package com.riwi.Simulacrum_SpringBoot_Test.util.messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ErrorMessage {
    
    private final String mensaje = "";

    public static String idNotFound(String entity) {
        final String message = "No hay registros en la entidad %s con el id suministrado";
        return String.format(message, entity);
    }
}
