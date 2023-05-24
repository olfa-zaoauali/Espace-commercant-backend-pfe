package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;

import java.util.List;
import java.util.Optional;

public interface SAdminservice {

    List<SAdmin> findAll();
    Optional<SAdmin> findByemail(String email);
    void delete(Integer id);
    SAdminResponsedto findByTeantId(String tenantId);
    SAdminResponsedto update(SAdminRequestdto sAdminRequestdto, Integer id) ;
    ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest);
    SAdmin updateenabled(Integer id);
    SAdmin updatenotenabled(Integer id);
    List<Commercant> SearchAllCommercant(String tenantId);
    public List<ClientResponseDto> SearchAllClients(String tenantId);
    public List<ClientResponseDto> getAllClients(String tenantId);
    public List<SAdminResponsedto> findall();
    double totalRevenu(String tenantId);
    int nbClients(String tenantId);
    public double revenuNet(String tenantId);

    }
