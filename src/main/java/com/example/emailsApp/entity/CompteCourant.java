package com.example.emailsApp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;





@Data

@EqualsAndHashCode(callSuper=false)

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("CC")


public class CompteCourant  extends CompteBancaire{

    private double decouvert;
    
}
