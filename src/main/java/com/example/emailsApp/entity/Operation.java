package com.example.emailsApp.entity;







import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity

public class Operation {

 @Id  
 @GeneratedValue(strategy = GenerationType.IDENTITY) 
 private long id;
 private Date date;

 private double montant;

 @Enumerated(EnumType.STRING)
 private OpetationType type;


 private String description;

 @ManyToOne
 private CompteBancaire compteBancaire;





    
}
