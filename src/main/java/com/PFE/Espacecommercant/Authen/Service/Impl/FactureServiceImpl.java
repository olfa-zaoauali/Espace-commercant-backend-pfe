package com.PFE.Espacecommercant.Authen.Service.Impl;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.CashoutRepository;
import com.PFE.Espacecommercant.Authen.Repository.CommercantRepository;
import com.PFE.Espacecommercant.Authen.Repository.FactureRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.FactureService;
import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements FactureService {
    @Autowired
    private final FactureRepository factureRepository;
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final CommercantRepository commercantRepository;

    @Override
    public List<Facture> getAll() {
        return factureRepository.findAll();
    }
    @Autowired
    private final CashoutRepository cashoutRepository;
    @Override
    public Facture save(String tenantId) {
        Facture facturee= new Facture();
        Admin admin= adminRepository.findByTenantId(tenantId).orElse(null);
        facturee.setPartenaire(admin);
        facturee.setDateFacture(LocalDate.now());
        facturee.setTva(19);
        facturee.setReference(facturee.generateID());
        facturee.setHt(admin.totalprix());
        facturee.setTtc(facturee.calculTotal(facturee.getHt(), facturee.getTva()));
        facturee.setTotalLettre(facturee.totalEnLettre(facturee.getTtc()));
        Facture saved =factureRepository.save(facturee);
        return saved;
    }

    @Override
    public Facture saveCashout() {
        Facture facture= new Facture();
        facture.setDateFacture(LocalDate.now());
        facture.setTva(19);
        facture.setReference(facture.generateID());
        facture.setTtc(facture.calculTotal(facture.getHt(), facture.getTva()));
        facture.setTotalLettre(facture.totalEnLettre(facture.getTtc()));
        Facture saved =factureRepository.save(facture);
        return saved;
    }
    @Override
    public void delete(Long id) {
        factureRepository.deleteById(id);

    }
    @Override
    public Facture findByPartenaire(String tenantId) {
        Admin admin= adminRepository.findByTenantId(tenantId).orElse(null);
        Facture facture= factureRepository.findByPartenaire(admin);
        return facture;
    }
    @Override
    public Facture findById(Long id){
        return factureRepository.findById(id).orElse(null);
    }

    @Override
    public Admin getAdminInfo(Long numero){
        Facture facture= factureRepository.findById(numero).orElse(null);
       return facture.getPartenaire();
    }

}
