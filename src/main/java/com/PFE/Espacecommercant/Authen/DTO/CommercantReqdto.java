package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Commercant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercantReqdto {
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String adresse;
    private String ville;
    private String image;
    private Double pay;
    private String sadminId;

}
