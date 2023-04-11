package com.PFE.Espacecommercant.Authen.Controller;
import com.PFE.Espacecommercant.Authen.DTO.RegisterAdminResponsedto;
import com.PFE.Espacecommercant.Authen.DTO.RegisterRequest;
import com.PFE.Espacecommercant.Authen.Service.facade.Adminservice;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/admins")
@CrossOrigin("http://localhost:4200")
public class AdminController {

    @Autowired
    private Adminservice adminservice;



    @GetMapping("")
    public List<Admin> getAdmins(){

        return adminservice.findAll();
    }
    @GetMapping("/commercants/{id}")
    public List<Commercant> getallcommercants(@PathVariable Integer id){
       return adminservice.SearchAllCommercant(id);
    }
    @GetMapping("/validation/{id}")
    public String activateAdmin(@PathVariable("id") Integer id) {
        adminservice.activateAdmin(id);
        return "User activated successfully";
    }
    @GetMapping("/{email}")
    public ResponseEntity<Optional<Admin>> findByEmail(@PathVariable String email) {
        Optional<Admin> Response=adminservice.findByemail(email);
        return ResponseEntity.ok(Response);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<RegisterAdminResponsedto> update(@RequestBody RegisterRequest adminRequestDto, @PathVariable Integer id) {
        RegisterAdminResponsedto adminResponsedto=adminservice.update(adminRequestDto, id);
        return ResponseEntity.accepted().body(adminResponsedto);

    }
    @PutMapping("/enabled/{id}")
    public ResponseEntity<Admin> updateenable(@PathVariable Integer id){
        Admin adminenabled=adminservice.updateenabled(id);
        return ResponseEntity.accepted().body(adminenabled);
    }
    @PutMapping("/notenabled/{id}")
    public ResponseEntity<Admin> updatenotenable(@PathVariable Integer id){
        Admin adminenabled=adminservice.updatenotenabled(id);
        return ResponseEntity.accepted().body(adminenabled);
    }






}
