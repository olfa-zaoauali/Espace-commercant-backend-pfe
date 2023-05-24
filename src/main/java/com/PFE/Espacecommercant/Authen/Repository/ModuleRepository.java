package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.model.Modules;
import com.PFE.Espacecommercant.Authen.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Modules,Integer> {
    List<Modules> findAllById(Integer id);
    @Override
    Optional<Modules> findById(Integer integer);}
