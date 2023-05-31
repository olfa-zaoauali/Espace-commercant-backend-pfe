package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.SAdminRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.ModuleRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.ModulesService;
import com.PFE.Espacecommercant.Authen.model.Modules;

import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import com.PFE.Espacecommercant.Authen.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ModulesServiceImpl implements ModulesService {
    @Autowired
    private final ModuleRepository moduleRepository;
    @Autowired
    private final AdminRepository adminRepository;

    @Override
    public Modules save( Modules modules) {
        var module= Modules.builder()
                .nom(modules.getNom())
                .prix(modules.getPrix())
                .reference(Modules.generateRandomReference())
                .build();
        Modules saved =moduleRepository.save(module);
        return saved;
    }

    @Override
    public List<Modules> getAll() {
        return moduleRepository.findAll();
    }
    @Override
    public List<Modules> getModulesOdAdmin(String tenantId){
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        return admin.getModules();
    }

    @Override
    public void delete(Integer id) {
         moduleRepository.deleteById(id);
    }
    @Override
    public Modules update(Modules modules, Integer id) {
        Modules modules1 = moduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Module id not found "+ id));
        Modules saved= new Modules();
        saved.setId(id);
        saved.setNom(modules.getNom());
        saved.setPrix(modules.getPrix());
        return  moduleRepository.save(saved);
    }

}
