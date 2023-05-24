package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.Service.facade.CashoutService;
import com.PFE.Espacecommercant.Authen.model.Cashout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/cashouts")
@CrossOrigin("http://localhost:4200")
public class CashoutController {
    @Autowired
    private CashoutService cashoutService;
    @PostMapping("/add/{tenantId}")
    private Cashout Savefacture(@PathVariable String tenantId){
        Cashout saved= cashoutService.save(tenantId);
        return saved;
    }
    @GetMapping("")
    private List<Cashout> GetAll(){
        return cashoutService.getAll();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        cashoutService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
