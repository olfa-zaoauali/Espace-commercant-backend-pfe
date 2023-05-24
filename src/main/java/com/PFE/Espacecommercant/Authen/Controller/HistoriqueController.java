package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.Service.facade.HistoriqueService;
import com.PFE.Espacecommercant.Authen.model.Historique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/historiques")
@CrossOrigin("http://localhost:4200")
public class HistoriqueController {
    @Autowired
    private HistoriqueService historiqueService;
    @GetMapping("/{tenantId}")
    public List<Historique> getHistoriqyeOfAdmin(@PathVariable String tenantId){
        return historiqueService.getHistoriques(tenantId);
    }
}
