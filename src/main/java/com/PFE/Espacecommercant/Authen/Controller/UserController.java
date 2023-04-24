package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.DTO.EmailResponse;
import com.PFE.Espacecommercant.Authen.Service.facade.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth/user")
@CrossOrigin("http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/{tenantId}")
    public EmailResponse getuseremail(@PathVariable String tenantId){

        return userService.FindUser(tenantId);
    }
}
