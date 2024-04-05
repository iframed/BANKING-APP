package com.example.emailsApp.security;

import javax.crypto.spec.SecretKeySpec;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;



@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurity {
    
    //passwordencoder
  @Bean
  public PasswordEncoder passwordEncoder (){
  
    return new BCryptPasswordEncoder();

  }
  
  
  //securityfilterchain pour securise lapp
  @Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{

  
   return httpSecurity

         .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .csrf(csrf->csrf.disable())
         .cors(Customizer.withDefaults())
         
         .authorizeHttpRequests(ar->ar.requestMatchers("**").permitAll())
         

         // Autoriser l'accès aux comptes sans authentification)
         .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
         .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
          .build();

  }

  //si on utilise jwt
  //encoder 
  @Bean
  JwtEncoder jwtEncoder(){
  

    String secretKey="2bjZjGh89l4JkqU5MnOdQX+sFpPzRnUDT6wEwPvlmT4";
    return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
   


  }



  // decoder
    @Bean
    JwtDecoder jwtDecoder(){

    String secretKey="2bjZjGh89l4JkqU5MnOdQX+sFpPzRnUDT6wEwPvlmT4";
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HMAC");
    return  NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS256).build();


    }

  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
    DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);

    return new ProviderManager(daoAuthenticationProvider);
  }


  @Bean
 CorsConfigurationSource corsConfigurationSource (){

  CorsConfiguration corsConfiguration = new CorsConfiguration();
  corsConfiguration.addAllowedOrigin("*");

  corsConfiguration.addAllowedMethod("*");
  corsConfiguration.addAllowedHeader("*");


 UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
 source.registerCorsConfiguration("/**", corsConfiguration);
 
 return source ;


 }

}
