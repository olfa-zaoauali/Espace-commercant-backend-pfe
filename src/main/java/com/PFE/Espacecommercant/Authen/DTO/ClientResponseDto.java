package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String company;
    private String domain;
    private Boolean enabled;
    private String logo;
    private Integer nbEmployer;
    private String adresse;
    private String ville;
    private String pays;
    private LocalDate dateExpiration;
    private LocalDate dateCreation;
    private String emailCommercant;
    private String tenantId;
    private boolean verified;

    @JsonIgnore
    private SAdmin sAdmin;
    @JsonIgnore
    private Commercant commercant;
    public static ClientResponseDto ClientToClientDto(Client client){
      ClientResponseDto  clientResponseDto = new ClientResponseDto();
      clientResponseDto.setId(client.getId());
        clientResponseDto.setFirstname(client.getFirstname());
        clientResponseDto.setLastname(client.getLastname());
        clientResponseDto.setEmail(client.getEmail());
        clientResponseDto.setLogo(client.getLogo());
        clientResponseDto.setCompany(client.getCompany());
        clientResponseDto.setDomain(client.getDomain());
        clientResponseDto.setEnabled(client.getEnabled());
        clientResponseDto.setTelephone(client.getTelephone());
        clientResponseDto.setNbEmployer(client.getNbEmployer());
        clientResponseDto.setDateExpiration(client.getDateExpiration());
        clientResponseDto.setDateCreation(client.getDateCreation());
        clientResponseDto.setEmailCommercant(client.getEmailCommercant());
        clientResponseDto.setTenantId(client.getTenantId());
        clientResponseDto.setAdresse(client.getAdresse());
        clientResponseDto.setVille(client.getVille());
        clientResponseDto.setPays(client.getPays());
        clientResponseDto.setVerified(client.isVerified());
        return clientResponseDto;

    }

}
