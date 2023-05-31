package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.CommercantResponsedto;
import com.PFE.Espacecommercant.Authen.Repository.CashoutRepository;
import com.PFE.Espacecommercant.Authen.Repository.CommercantRepository;
import com.PFE.Espacecommercant.Authen.Repository.FactureRepository;
import com.PFE.Espacecommercant.Authen.Repository.HistoriqueRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.CashoutService;
import com.PFE.Espacecommercant.Authen.Service.facade.FactureService;
import com.PFE.Espacecommercant.Authen.model.Cashout;
import com.PFE.Espacecommercant.Authen.model.Facture;

import com.PFE.Espacecommercant.Authen.model.Historique;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CashoutServiceImpl implements CashoutService {
    @Autowired
    private final CashoutRepository cashoutRepository;
    @Autowired
    private final CommercantRepository commercantRepository;
    @Autowired
    private final HistoriqueRepository historiqueRepository;
    @Autowired
    private final FactureRepository factureRepository;


    @Override
    public List<Cashout> getAll() {
        return cashoutRepository.findAll();
    }
    @Override
    public  Cashout findById(Integer id){
        Cashout cashout= cashoutRepository.findById(id).orElse(null);
        return  cashout;
    }
    @Override
    public Cashout save(String tenantId) {
        Commercant commercant= commercantRepository.findByTenantId(tenantId).orElse(null);
        var cashout= Cashout.builder()
                .commercant(commercant)
                .cashout(commercant.getPay())
                .temp(LocalTime.now())
                .dateDeCreation(LocalDate.now())
                .Montant(100)
                .verified(false)
                .build();
        if (cashout.getCashout()>cashout.getMontant()){
            cashoutRepository.save(cashout);
            Facture facture= new Facture();
            facture.setDateFacture(LocalDate.now());
            facture.setHt(cashout.getCashout());
            facture.setTva(19);
            facture.setReference(facture.generateID());
            facture.setTtc(facture.calculTotal(facture.getHt(), facture.getTva()));
            facture.setTotalLettre(facture.totalEnLettre(cashout.getCashout()));
            facture.setCashout(cashout);
            factureRepository.save(facture);
            Historique historique= new Historique();
            if(commercant.getSadmin()!=(null)){
                historique.setAdminId(commercant.getSadmin().getTenantId());
            }
            if (commercant.getAdmin()!=(null)){
                historique.setAdminId(commercant.getAdmin().getTenantId());
            }
            historique.setDate(LocalDate.now());
            historique.setTemp(LocalTime.now());
            historique.setMessage("Le/La Commerçant(e) "+commercant.getLastname()+" "+commercant.getFirstname()+
                    " a demandé(e) un cashout de "+cashout.getCashout()+" DT le " + historique.getDate()+" à "+historique.getTemp());
            historiqueRepository.save(historique);
            cashout.setFacture(facture);
            commercant.setPay(0.0);
            commercantRepository.save(commercant);
            cashoutRepository.save(cashout);
            return cashout;
        }
        else return null;
    }
    @Override
    public void delete(Integer id) {
        cashoutRepository.deleteById(id);
    }

}
