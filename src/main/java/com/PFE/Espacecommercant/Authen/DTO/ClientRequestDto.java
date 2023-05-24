package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {
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
    private String commercantId;
    public static Client DtoToClient(ClientRequestDto clientRequestDto){
        Client cliententity=new Client();
        cliententity.setEmail(clientRequestDto.getEmail());
        cliententity.setDomain(clientRequestDto.getDomain());
        cliententity.setCompany(clientRequestDto.getCompany());
        cliententity.setTelephone(clientRequestDto.getTelephone());
        cliententity.setNbEmployer(clientRequestDto.getNbEmployer());
        cliententity.setEmailCommercant(clientRequestDto.getEmailCommercant());
        cliententity.setLastname(clientRequestDto.getLastname());
        cliententity.setFirstname(clientRequestDto.getFirstname());
        cliententity.setLogo(clientRequestDto.getLogo());
        return cliententity;
    }
}
