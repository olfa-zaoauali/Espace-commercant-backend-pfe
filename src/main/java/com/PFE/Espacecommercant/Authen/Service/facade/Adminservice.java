package com.PFE.Espacecommercant.Authen.Service.facade;
import com.PFE.Espacecommercant.Authen.DTO.RegisterAdminResponsedto;
import com.PFE.Espacecommercant.Authen.DTO.RegisterRequest;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Commercant;


import java.util.List;
import java.util.Optional;

public interface Adminservice {
    List<Admin> findAll();
    void activateAdmin(Integer id);
    Optional<Admin> findByemail(String email);
    RegisterAdminResponsedto update(RegisterRequest adminRequestDto, Integer id) ;
    Admin updateenabled(Integer id);
    Admin updatenotenabled(Integer id);
    List<Commercant> SearchAllCommercant(Integer id);





}
