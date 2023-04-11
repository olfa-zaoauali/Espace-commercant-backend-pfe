package com.PFE.Espacecommercant.Authen.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminResponsedto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String company;
    private String domain;
    private String matricule;
    private String batinda;
    private String logo;
    private Boolean enabled;
}
