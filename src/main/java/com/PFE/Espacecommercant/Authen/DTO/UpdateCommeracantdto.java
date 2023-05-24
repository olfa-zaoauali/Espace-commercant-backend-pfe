package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class UpdateCommeracantdto {

    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String adresse;
    private String ville;
    private String pays;

    public static Commercant mapperdtotocom(UpdateCommeracantdto updateCommeracantdto){
        Commercant commercantentity = new Commercant();
        commercantentity.setFirstname(updateCommeracantdto.getFirstname());
        commercantentity.setLastname(updateCommeracantdto.getLastname());
        commercantentity.setEmail(updateCommeracantdto.getEmail());
        commercantentity.setTelephone(updateCommeracantdto.getTelephone());
        commercantentity.setAdresse(updateCommeracantdto.getAdresse());
        commercantentity.setVille(updateCommeracantdto.getVille());
        commercantentity.setPays(updateCommeracantdto.getPays());
        return commercantentity;
    }
}
