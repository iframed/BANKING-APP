package com.example.emailsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.emailsApp.entity.CompteBancaire;



@Repository
public interface CompteRepo extends JpaRepository<CompteBancaire , String>{
    
}
