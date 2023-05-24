package com.PFE.Espacecommercant.Authen.Controller;
import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Service.facade.Adminservice;
import com.PFE.Espacecommercant.Authen.model.Modules;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/commercants/{tenantId}")
    public List<Commercant> getallcommercants(@PathVariable String tenantId){
       return adminservice.SearchAllCommercant(tenantId);
    }
    @GetMapping("/commercantsnb/{tenantId}")
    public int getnbCommercants(@PathVariable String tenantId){
        return adminservice.nbCommercants(tenantId);
    }
    @GetMapping("/verified/{tenantId}")
    public int getnbclientverified(@PathVariable String tenantId){
        return adminservice.nbClientsVerified(tenantId);
    }
    @GetMapping("/tenantId/{tenantId}")
    public RegisterAdminResponsedto getByTeantId(@PathVariable String tenantId){
        return adminservice.findByTeantId(tenantId);
    }
    @GetMapping("/modules/{tenantId}")
    public List<Modules> getallmodules(@PathVariable String tenantId){
        return adminservice.SearchAllModules(tenantId);
    }
    @GetMapping("/clients/{tenantId}")
    public List<Client> getallclients(@PathVariable String tenantId){
        return adminservice.getallclients(tenantId);
    }
    @GetMapping("/validation/{id}")
    public String activateAdmin(@PathVariable("id") Integer id) {
        adminservice.activateAdmin(id);
        return "User activated successfully";
    }
    @GetMapping("/{email}")
    public Optional<Admin> findByEmail(@PathVariable String email) {
        Optional<Admin> Response=adminservice.findByemail(email);
        return Response;
    }
    @GetMapping("/get/{company}")
    public Admin findByCompany(@PathVariable String company) {
        Admin Response=adminservice.findByCompany(company);
        return Response;
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<RegisterAdminResponsedto> update(@RequestBody RegisterRequest adminRequestDto, @PathVariable Integer id) {
        RegisterAdminResponsedto adminResponsedto=adminservice.update(adminRequestDto, id);
        return ResponseEntity.accepted().body(adminResponsedto);

    }
    @PutMapping("password/{tenantId}")
    public ChangePasswordRequest changerPassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable String tenantId){
        ChangePasswordRequest Response= adminservice.changerPassword(tenantId,changePasswordRequest);
        return Response;
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
    @GetMapping("/prix/{tenantId}")
    public double totalPrix(@PathVariable String tenantId){
        return adminservice.totalprix(tenantId);
    }
    @GetMapping("/revenu/{tenantId}")
    public double totalRevenu(@PathVariable String tenantId){
        return adminservice.totalRevenu(tenantId);
    }
    @GetMapping("/nb")
    public int totalnbAdmins(){
        return adminservice.nbAdmin();
    }
    @GetMapping("/revenuNet/{tenantId}")
    public double totalRevenuNet(@PathVariable String tenantId){
        return adminservice.revenuNet(tenantId);
    }
    @GetMapping("/nbClients/{tenantId}")
    public double nbClients(@PathVariable String tenantId){
        return adminservice.nbClients(tenantId);
    }
    @PostMapping("/factureClient/{id}")
    public FactureResponseDto validerClient(@PathVariable Integer id,@RequestBody FactureRequestDto dto){
        return adminservice.valideClient(id,dto);
    }
}
