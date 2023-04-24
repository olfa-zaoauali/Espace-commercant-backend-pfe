package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SAdminRepository extends JpaRepository<SAdmin, Integer> {
    public List<SAdmin> findAll();
    @Override
    Optional<SAdmin> findById(Integer id);
    Optional<SAdmin> findByemail(String email);
    Optional<SAdmin> findByTenantId(String tenantId);

}
