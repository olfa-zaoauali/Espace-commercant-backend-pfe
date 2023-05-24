package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.HistoriqueRepository;
import com.PFE.Espacecommercant.Authen.Repository.SAdminRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.HistoriqueService;
import com.PFE.Espacecommercant.Authen.model.Historique;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueServiceImpl implements HistoriqueService {
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final SAdminRepository sAdminRepository;
    @Autowired
    private final HistoriqueRepository historiqueRepository;
    @Override
    public List<Historique>  getHistoriques(  String tenantId) {
        if(adminRepository.findByTenantId(tenantId)!=(null)){
            List<Historique> historiques=    historiqueRepository.getAllByAdminId(tenantId);
            return historiques;
        }
        if(sAdminRepository.findByTenantId(tenantId)!=(null)){
            List<Historique> historiques=    historiqueRepository.getAllByAdminId(tenantId);
            return historiques;
        }
        return null;
    }

}
