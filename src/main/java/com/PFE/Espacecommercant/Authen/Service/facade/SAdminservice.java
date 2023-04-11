package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.CommercantResponsedto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;

import java.util.List;
import java.util.Optional;

public interface SAdminservice {

    List<SAdminResponsedto> findAll();
    Optional<SAdmin> findByemail(String email);
    void delete(Integer id);
    SAdminResponsedto update(SAdminRequestdto sAdminRequestdto, Integer id) ;
    SAdmin updateenabled(Integer id);
    SAdmin updatenotenabled(Integer id);

}
