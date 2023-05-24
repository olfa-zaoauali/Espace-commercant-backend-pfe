package com.PFE.Espacecommercant.Authen.Controller;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.CashoutService;
import com.PFE.Espacecommercant.Authen.Service.facade.FactureService;
import com.PFE.Espacecommercant.Authen.model.Cashout;
import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/factures")
@CrossOrigin("http://localhost:4200")
public class FactureController {
    @Autowired
    private FactureService factureService;
    @Autowired
    private CashoutService cashoutService;


    @PostMapping("/add/{tenantId}")
    private Facture Savefacture(@PathVariable String tenantId){
        Facture saved= factureService.save(tenantId);
        return saved;
    }

    @GetMapping("")
    private List<Facture> GetAll(){
        return factureService.getAll();
    }
    @GetMapping("/admin/{tenantId}")
    private Facture findbyPartenaire(@PathVariable String tenantId){
        return factureService.findByPartenaire(tenantId);
    }
    @GetMapping("/commercant/{id}")
    private Facture findbyCommercant(@PathVariable Integer id){
         Cashout cashout= cashoutService.findById(id);
         return  cashout.getFacture();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        factureService.delete(id);
        return  ResponseEntity.noContent().build();
    }
    @GetMapping("/adminInfo/{id}")
    private Admin getAdmin(@PathVariable Long id){
        return factureService.getAdminInfo(id);
    }
    @GetMapping("/number/{id}")
    private Facture getFactureById(@PathVariable Long id){
        return factureService.findById(id);
    }

}
