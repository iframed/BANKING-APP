package com.example.emailsApp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.emailsApp.entity.Operation;
import java.util.List;

@Repository
public interface OperationRepo extends JpaRepository <Operation,Long>{
    
     List<Operation> findByCompteBancaireId(String id);
     Page<Operation> findByCompteBancaireIdOrderByDateDesc(String id, Pageable pageable);
}
