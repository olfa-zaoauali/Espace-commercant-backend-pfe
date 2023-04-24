package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.EmailResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    EmailResponse FindUser(String tenantId);
}
