package com.PFE.Espacecommercant.Authen.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SAdminRequestdto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String image ;
}
