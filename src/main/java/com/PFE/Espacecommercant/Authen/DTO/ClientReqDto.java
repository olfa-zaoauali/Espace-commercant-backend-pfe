package com.PFE.Espacecommercant.Authen.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String emailCommercant;
    private String sAdminId;

}
