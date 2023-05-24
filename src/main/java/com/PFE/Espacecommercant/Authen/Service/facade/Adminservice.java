package com.PFE.Espacecommercant.Authen.Service.facade;
import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.model.Modules;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;


import java.util.List;
import java.util.Optional;

public interface Adminservice {
    List<Admin> findAll();
    void activateAdmin(Integer id);
    RegisterAdminResponsedto findByTeantId(String tenantId);
    Optional<Admin> findByemail(String email);
    Admin findByCompany(String company);
    RegisterAdminResponsedto update(RegisterRequest adminRequestDto, Integer id) ;
    ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest);
    Admin updateenabled(Integer id);
    Admin updatenotenabled(Integer id);
    List<Commercant> SearchAllCommercant(String tenantId);
    List<Modules> SearchAllModules(String tenantId);
    List<Client> getallclients(String tenantId);
    double totalprix(String tenantId);
    double totalRevenu(String tenantId);
    public int nbAdmin();
    public double revenuNet(String tenantId);
    public int nbClients(String tenantId);
    public FactureResponseDto valideClient(Integer id, FactureRequestDto dto);
    int nbCommercants(String tenantId);
    int nbClientsVerified(String tenantId);
    }
