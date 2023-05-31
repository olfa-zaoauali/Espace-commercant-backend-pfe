package com.PFE.Espacecommercant.Authen.DTO;

import com.PFE.Espacecommercant.Authen.model.Cashout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashoutResponseDto {
    private Integer id;
    private double cashout;
    private LocalTime temp;
    private LocalDate dateDeCreation;
    private double Montant;
    private boolean verified;
    private String emailCommercant;
    private String firstnameCom;
    private String lastnameCom;
    private String tenantId;
    public static CashoutResponseDto mapperfromEntityToDto(Cashout cashout){
        CashoutResponseDto dto = new CashoutResponseDto();
        dto.setCashout(cashout.getCashout());
        dto.setId(cashout.getId());
        dto.setMontant(cashout.getMontant());
        dto.setDateDeCreation(cashout.getDateDeCreation());
        dto.setVerified(cashout.isVerified());
        dto.setEmailCommercant(cashout.getCommercant().getEmail());
        dto.setFirstnameCom(cashout.getCommercant().getFirstname());
        dto.setLastnameCom(cashout.getCommercant().getLastname());
        dto.setTemp(cashout.getTemp());
        dto.setTenantId(cashout.getCommercant().getTenantId());
        return dto;
    }

}
