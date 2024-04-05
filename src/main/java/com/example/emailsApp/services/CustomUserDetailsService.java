package com.example.emailsApp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.emailsApp.entity.Roles;
import com.example.emailsApp.entity.User;
import com.example.emailsApp.repository.UserRepository;



@Service

public class CustomUserDetailsService implements UserDetailsService{
    

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    getAuthorities(user.getRoles()));
                    //mapRolesToAuthorities(user.getRoles()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
    
    /*private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Roles> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
     } */


     private Collection<? extends GrantedAuthority> getAuthorities(
  Collection<Roles> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Roles role: roles) {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
       
                 
    }
    
    return authorities;
}
}
