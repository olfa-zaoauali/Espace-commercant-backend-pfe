package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.users.Commercant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommercantRepository extends JpaRepository<Commercant, Integer> {
    public List<Commercant> findAll();
    @Override
    Optional<Commercant> findById(Integer id);

    Optional<Commercant> findByemail(String email);
    Optional<Commercant> findByTenantId(String tenantId);

}
