package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.DTO.SAdminRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.Service.facade.ModulesService;
import com.PFE.Espacecommercant.Authen.model.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/modules")
@CrossOrigin("http://localhost:4200")
public class ModulesController {
    @Autowired
    private ModulesService modulesService;
    @PostMapping("/add")
    private Modules SaveModules(@RequestBody Modules modules){
        Modules saved= modulesService.save(modules);
        return saved;
    }
    @GetMapping("")
    private List<Modules>  GetAll(){
        return modulesService.getAll();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        modulesService.delete(id);
        return  ResponseEntity.noContent().build();
    }
    @PutMapping("update/{id}")
    public  ResponseEntity<Modules> update(@RequestBody Modules modules, @PathVariable Integer id){
        Modules module=modulesService.update(modules, id);
        return ResponseEntity.accepted().body(module);

    }
    @GetMapping("Admin/{tenantId}")
    public List<Modules> getallModulesOfAdmin(@PathVariable String tenantId){
        return modulesService.getModulesOdAdmin(tenantId);
    }

}
