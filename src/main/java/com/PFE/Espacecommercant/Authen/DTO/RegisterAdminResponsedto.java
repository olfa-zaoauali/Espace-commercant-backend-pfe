package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String adresse;
    private String ville;
    private String pays;
    private double apayer;
    private Integer nbEmployer;
    private String TenantId;
    public static RegisterAdminResponsedto AdminTodto(Admin admin){
        RegisterAdminResponsedto dto=new RegisterAdminResponsedto();
        dto.setFirstname(admin.getFirstname());
        dto.setLastname(admin.getLastname());
        dto.setEmail(admin.getEmail());
        dto.setEnabled(admin.getEnabled());
        dto.setAdresse(admin.getAdresse());
        dto.setVille(admin.getVille());
        dto.setPays(admin.getPays());
        dto.setLogo(admin.getLogo());
        dto.setId(admin.getId());
        dto.setTelephone(admin.getTelephone());
        dto.setTenantId(admin.getTenantId());
        dto.setMatricule(admin.getMatricule());
        dto.setCompany(admin.getCompany());
        dto.setDomain(admin.getDomain());
        dto.setApayer(admin.getApayer());
        dto.setBatinda(admin.getBatinda());
        dto.setNbEmployer(admin.getNbEmployer());
        return dto;
    }




    }
