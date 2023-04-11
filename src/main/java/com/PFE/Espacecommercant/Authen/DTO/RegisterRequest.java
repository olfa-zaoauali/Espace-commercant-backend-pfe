package com.PFE.Espacecommercant.Authen.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String telephone;
    private String company;
    private String domain;
    private String matricule;
    private String batinda;
    private boolean enabled=false;
    private String logo;

    public Boolean getEnabled() {
        return enabled;
    }
}
