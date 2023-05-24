package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.Admin;

import java.util.List;

public interface FactureService {
    List<Facture> getAll();
    public Facture save(String tenantId);
    public void delete(Long id);
    Facture  findByPartenaire(String tenantId);
    public Facture saveCashout() ;
    public Admin getAdminInfo(Long numero);
    public Facture findById(Long id);

    }
