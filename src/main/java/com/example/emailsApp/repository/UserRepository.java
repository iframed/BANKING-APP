package com.example.emailsApp.repository;

import com.example.emailsApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);

}
