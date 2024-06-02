package com.riwi.Simulacrum_SpringBoot_Test.api.dto.response;

import com.riwi.Simulacrum_SpringBoot_Test.util.enums.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicResp {
    
    private Long user_id;

    private String user_name;

    private String password;

    private String email;

    private String full_name;

    private Rol role;
}
