package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.model.Cashout;
import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;

import java.util.List;
import java.util.Optional;

public interface CommercantService {
    List<Commercant> findAll();
    void activateCommercant(Integer id);
    Optional<Commercant> findByemail(String email);
    Optional<Commercant> findByid(Integer id);
    Commercant findByTeantId(String tenantId);
    void delete(Integer id);
    CommercantResponsedto update(CommercantRequestdto commercantRequestdto, Integer id) ;
    CommercantResponsedto updatecommercant(CommercantReqdto commercantRequestdto, Integer id) ;
    CommercantResponsedto updateCompte(UpdateCommeracantdto commercantRequestdto, String tenantId);
    ChangePasswordRequest changerPassword(String tenantId,ChangePasswordRequest changePasswordRequest );
    Commercant changerImage(String tenantId,String Image);
    Commercant updateenabled(Integer id);
    Commercant updatenotenabled(Integer id);
    List<Client> SearchAllclient(String tenantId);
    List<Cashout> SearchAllCashouts(String tenantId);
    double calculCommission(String tenantId);
    double calculRevenu(String tenantId);
    int calculNbAdmins(String tenantId) ;
    int calculNbClients(String tenantId) ;
    List<FactureResponseDto> ConsulterFactures(String tenantId);
    public List<Admin> getAdminsOfCommercant(String tenantId);
    public RegisterAdminResponsedto getInfoAdmin(String tenantId);

    }
