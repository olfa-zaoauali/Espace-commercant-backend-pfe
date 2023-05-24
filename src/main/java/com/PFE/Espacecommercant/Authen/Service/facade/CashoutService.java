package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.model.Cashout;
import com.PFE.Espacecommercant.Authen.users.Commercant;

import java.util.List;

public interface CashoutService {
    List<Cashout> getAll();
    public Cashout save(String tenantId);
    public void delete(Integer id);
    public Cashout findById(Integer id);

    }
