package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.CommercantReqdto;
import com.PFE.Espacecommercant.Authen.DTO.CommercantRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.CommercantResponsedto;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;

import java.util.List;
import java.util.Optional;

public interface CommercantService {
    List<Commercant> findAll();
    void activateCommercant(Integer id);
    Optional<Commercant> findByemail(String email);
    Optional<Commercant> findByid(Integer id);

    void delete(Integer id);
    CommercantResponsedto update(CommercantRequestdto commercantRequestdto, Integer id) ;
    CommercantResponsedto updatecommercant(CommercantReqdto commercantRequestdto, Integer id) ;

    Commercant updateenabled(Integer id);
    Commercant updatenotenabled(Integer id);
    List<Client> SearchAllclient(String tenantId);


}
