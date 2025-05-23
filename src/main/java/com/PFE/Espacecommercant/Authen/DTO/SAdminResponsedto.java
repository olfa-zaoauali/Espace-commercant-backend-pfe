package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SAdminResponsedto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String telephone;
    private String adresse;
    private String ville;
    private String pays;
    private String image ;
    private String tenantId;
    private Boolean enabled=true;
    @JsonIgnore
    private List<Commercant> commercantList;
    @JsonIgnore
    private List<Client> clientList;
    public static SAdminResponsedto sadminTosadminDto(SAdmin sAdmin){
        SAdminResponsedto sAdminResponsedto=new SAdminResponsedto();
        sAdminResponsedto.setFirstname(sAdmin.getFirstname());
        sAdminResponsedto.setLastname(sAdmin.getLastname());
        sAdminResponsedto.setEmail(sAdmin.getEmail());
        sAdminResponsedto.setPassword(sAdmin.getPassword());
        sAdminResponsedto.setAdresse(sAdmin.getAdresse());
        sAdminResponsedto.setVille(sAdmin.getVille());
        sAdminResponsedto.setPays(sAdmin.getPays());
        sAdminResponsedto.setImage(sAdmin.getImage());
        sAdminResponsedto.setTenantId(sAdmin.getTenantId());
        sAdminResponsedto.setId(sAdmin.getId());
        sAdminResponsedto.setTelephone(sAdmin.getTelephone());
        List<Client> clientList= new ArrayList<>();
        List<Commercant> commercantList= sAdmin.getCommercants();
        for (Commercant commercant:commercantList){
            clientList.addAll(commercant.getClients());
        }
        sAdminResponsedto.setCommercantList(commercantList);
        sAdminResponsedto.setClientList(clientList);
        return sAdminResponsedto;
    }
}
