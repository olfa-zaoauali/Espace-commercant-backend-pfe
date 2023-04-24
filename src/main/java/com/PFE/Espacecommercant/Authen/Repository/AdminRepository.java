package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    List<Admin> findAll();
    @Override
    Optional<Admin> findById(Integer integer);

    Optional<Admin> findByemail(String email);
    Optional<Admin> findByTenantId(String tenantId);



}
