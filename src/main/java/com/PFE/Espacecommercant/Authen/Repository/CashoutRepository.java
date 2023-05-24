package com.PFE.Espacecommercant.Authen.Repository;

import com.PFE.Espacecommercant.Authen.model.Cashout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashoutRepository extends JpaRepository<Cashout,Integer> {

}
