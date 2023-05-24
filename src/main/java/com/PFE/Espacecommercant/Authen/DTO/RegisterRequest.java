package com.PFE.Espacecommercant.Authen.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

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
    private Integer nbEmployer;
    private boolean enabled=false;
    private String logo;
    private String adresse;
    private String ville;
    private String pays;
    private List<Integer> moduleId;

    public Boolean getEnabled() {
        return enabled;
    }

}
