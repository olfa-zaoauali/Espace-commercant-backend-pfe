package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.model.Modules;

import java.util.List;

public interface ModulesService {
    public Modules save(Modules modules);
    public List<Modules> getAll();
    public void delete(Integer id);
    public Modules update(Modules modules, Integer id);

    }
