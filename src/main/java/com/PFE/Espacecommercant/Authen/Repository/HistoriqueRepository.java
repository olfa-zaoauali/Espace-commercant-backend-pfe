package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique,Integer> {
    List<Historique> getAllByAdminId(String adminId);
}
