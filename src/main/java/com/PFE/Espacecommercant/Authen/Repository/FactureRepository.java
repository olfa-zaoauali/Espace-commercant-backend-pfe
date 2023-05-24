package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {
    List<Facture> findAll();
    Facture  findByPartenaire(Admin admin);

}
