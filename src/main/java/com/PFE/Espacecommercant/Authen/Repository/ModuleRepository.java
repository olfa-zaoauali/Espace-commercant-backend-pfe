package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.model.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Modules,Integer> {
    List<Modules> findAllById(Integer id);
}
