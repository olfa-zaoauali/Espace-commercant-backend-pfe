package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Commercant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercantResponsedto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String adresse;
    private String ville;
    private String image;
    private Double pay;
    private boolean enabled;
    private Integer admin;
    private Integer sadminid;


    public static CommercantResponsedto mapperfromEntityToDto(Commercant commercant){
        CommercantResponsedto dto = new CommercantResponsedto();
        dto.setId(commercant.getId());
        dto.setAdmin(commercant.getAdmin().getId());
        dto.setAdresse(commercant.getAdresse());
        dto.setFirstname(commercant.getFirstname());
        dto.setLastname(commercant.getLastname());
        dto.setEmail(commercant.getEmail());
        dto.setVille(commercant.getVille());
        dto.setEnabled(commercant.getEnabled());
        dto.setTelephone(commercant.getTelephone());
        dto.setImage(commercant.getImage());
        dto.setPay(commercant.getPay());
    return dto;

    }

}
