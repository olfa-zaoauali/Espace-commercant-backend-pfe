package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientReqDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String telephone;
    private String company;
    private String domain;
    private String logo;
    private Integer nbEmployer;
    private String adresse;
    private String ville;
    private String pays;
    private String emailCommercant;
    private String sAdminId;
    private String commercantId;

    public static Client DtoToClient(ClientReqDto clientRequestDto){
        Client cliententity=new Client();
        cliententity.setEmail(clientRequestDto.getEmail());
        cliententity.setDomain(clientRequestDto.getDomain());
        cliententity.setCompany(clientRequestDto.getCompany());
        cliententity.setTelephone(clientRequestDto.getTelephone());
        cliententity.setNbEmployer(clientRequestDto.getNbEmployer());
        cliententity.setLastname(clientRequestDto.getLastname());
        cliententity.setFirstname(clientRequestDto.getFirstname());
        cliententity.setAdresse(clientRequestDto.getAdresse());
        cliententity.setVille(clientRequestDto.getVille());
        cliententity.setPays(clientRequestDto.getPays());
        return cliententity;
    }
    public static Admin ClientToAdmin(Client client){
        Admin admin=new Admin();
        admin.setFirstname(client.getFirstname());
        admin.setLastname(client.getLastname());
        admin.setEmail(client.getEmail());
        admin.setCompany(client.getCompany());
        admin.setDomain(client.getDomain());
        admin.setTelephone(client.getTelephone());
        admin.setTenantId(client.getTenantId());
        admin.setNbEmployer(client.getNbEmployer());
        admin.setAdresse(client.getAdresse());
        admin.setVille(client.getVille());
        admin.setPays(client.getPays());

        return admin;
    }

}
