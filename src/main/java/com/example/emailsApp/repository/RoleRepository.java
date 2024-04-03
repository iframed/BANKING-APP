package com.example.emailsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emailsApp.entity.Roles;

public interface RoleRepository  extends JpaRepository<Roles, Long>{
    Roles findByName(String name);
}
