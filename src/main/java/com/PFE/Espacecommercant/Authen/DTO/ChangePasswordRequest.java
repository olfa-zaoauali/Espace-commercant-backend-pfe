package com.PFE.Espacecommercant.Authen.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ChangePasswordRequest {
    private String passwordNew;
    private String passwordActuel;
    private String passwordConfirm;
}
