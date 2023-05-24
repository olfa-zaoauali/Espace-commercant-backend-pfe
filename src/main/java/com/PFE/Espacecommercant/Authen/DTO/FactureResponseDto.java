package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureResponseDto {
    private Integer numero;
    private LocalDate dateFacture;
    private double ttc;
    private double ht;
    private double tva;
    private String totalLettre;
    private String reference;
    private String emailAdmmin;
    private String companyAdmin;
    private String logoAdmin;
    public static FactureResponseDto mapperfromEntityToDto(Facture facture){
        FactureResponseDto dto = new FactureResponseDto();
        dto.setDateFacture(facture.getDateFacture());
        dto.setNumero(facture.getNumero());
        dto.setHt(facture.getHt());
        dto.setTva(facture.getTva());
        dto.setTtc(facture.getTtc());
        dto.setTotalLettre(facture.getTotalLettre());
        dto.setReference(facture.getReference());
        Admin admin=facture.getPartenaire();
        dto.setEmailAdmmin(admin.getEmail());
        dto.setCompanyAdmin(admin.getCompany());
        dto.setLogoAdmin(admin.getLogo());
        return dto;
    }
    public static FactureResponseDto EntityToDto(Facture facture){
        FactureResponseDto dto = new FactureResponseDto();
        dto.setDateFacture(facture.getDateFacture());
        dto.setNumero(facture.getNumero());
        dto.setHt(facture.getHt());
        dto.setTva(facture.getTva());
        dto.setTtc(facture.getTtc());
        dto.setTotalLettre(facture.getTotalLettre());
        dto.setReference(facture.getReference());
        Client client=facture.getClient();
        dto.setEmailAdmmin(client.getEmail());
        dto.setCompanyAdmin(client.getCompany());
        dto.setLogoAdmin(client.getLogo());
        return dto;
    }
}
