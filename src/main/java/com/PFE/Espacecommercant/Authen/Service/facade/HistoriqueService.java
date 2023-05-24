package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.model.Historique;

import java.util.List;

public interface HistoriqueService {
    List<Historique> getHistoriques(String tenantId);
}
