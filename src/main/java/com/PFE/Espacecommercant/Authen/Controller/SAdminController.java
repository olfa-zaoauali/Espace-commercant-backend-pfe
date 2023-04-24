package com.PFE.Espacecommercant.Authen.Controller;


import com.PFE.Espacecommercant.Authen.DTO.ClientResponseDto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.Service.facade.SAdminservice;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/sadmins")
@CrossOrigin("http://localhost:4200")
public class SAdminController {
    @Autowired
    private SAdminservice sAdminservice;
    @GetMapping("")
    public List<SAdminResponsedto> getSAdmins(){

        return sAdminservice.findall();
    }
    @GetMapping("/admin")
    public List<SAdmin> getSAdmins1(){

        return sAdminservice.findAll();
    }
    @GetMapping("/commercants/{tenantId}")
    public List<Commercant> getallcommercants(@PathVariable String tenantId){
        return sAdminservice.SearchAllCommercant(tenantId);
    }
    @GetMapping("/clients/{tenantId}")
    public List<ClientResponseDto> getallclients(@PathVariable String tenantId){
        return sAdminservice.SearchAllClients(tenantId);
    }
    @GetMapping("/get/{tenantId}")
    public List<ClientResponseDto> Clients(@PathVariable String tenantId){
        return sAdminservice.getAllClients(tenantId);
    }


    @GetMapping("/{email}")
    public ResponseEntity<Optional<SAdmin>> findByEmail(@PathVariable String email) {
        Optional<SAdmin> Response=sAdminservice.findByemail(email);
        return ResponseEntity.ok(Response);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
       sAdminservice.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/enabled/{id}")
    public ResponseEntity<SAdmin> updateenable(@PathVariable Integer id){
        SAdmin sAdminenabled=sAdminservice.updateenabled(id);
        return ResponseEntity.accepted().body(sAdminenabled);
    }
    @PutMapping("/notenabled/{id}")
    public ResponseEntity<SAdmin> updatenotenable(@PathVariable Integer id){
        SAdmin sAdminnotenabled=sAdminservice.updatenotenabled(id);
        return ResponseEntity.accepted().body(sAdminnotenabled);
    }
    @PutMapping("update/{id}")
    public  ResponseEntity<SAdminResponsedto> update(@RequestBody SAdminRequestdto sAdminRequestdto, @PathVariable Integer id){
        SAdminResponsedto sAdminResponsedto=sAdminservice.update(sAdminRequestdto, id);
        return ResponseEntity.accepted().body(sAdminResponsedto);

    }

}
