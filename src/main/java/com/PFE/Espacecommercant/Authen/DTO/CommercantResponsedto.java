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
    private String pays;
    private String image;
    private Double pay;
    private double pourcentage;
    private boolean enabled;
    private String admin;
    private String sadminId;

    public static CommercantResponsedto mapperfromEntityToDto(Commercant commercant){
        CommercantResponsedto dto = new CommercantResponsedto();
        dto.setId(commercant.getId());
        dto.setAdmin(commercant.getAdmin().getTenantId());
        dto.setAdresse(commercant.getAdresse());
        dto.setFirstname(commercant.getFirstname());
        dto.setLastname(commercant.getLastname());
        dto.setEmail(commercant.getEmail());
        dto.setVille(commercant.getVille());
        dto.setPays(commercant.getPays());
        dto.setEnabled(commercant.getEnabled());
        dto.setTelephone(commercant.getTelephone());
        dto.setPourcentage(commercant.getPourcentage());
        dto.setImage(commercant.getImage());
        dto.setPay(commercant.getPay());
    return dto;

    }
    public static CommercantResponsedto mapperfromcomToDto(Commercant commercant){
        CommercantResponsedto dto = new CommercantResponsedto();
        dto.setId(commercant.getId());
        dto.setSadminId(commercant.getSadmin().getTenantId());
        dto.setAdresse(commercant.getAdresse());
        dto.setFirstname(commercant.getFirstname());
        dto.setLastname(commercant.getLastname());
        dto.setEmail(commercant.getEmail());
        dto.setVille(commercant.getVille());
        dto.setPays(commercant.getPays());
        dto.setEnabled(commercant.getEnabled());
        dto.setTelephone(commercant.getTelephone());
        dto.setImage(commercant.getImage());
        dto.setPay(commercant.getPay());
        dto.setPourcentage(commercant.getPourcentage());
        return dto;
    }

}
