package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    List<Client> findAll();
    @Override
    Optional<Client> findById(Integer integer);

    Client findByemail(String email);
    Client findByTenantId(String tenantId);
}
