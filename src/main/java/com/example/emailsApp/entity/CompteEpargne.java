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

@DiscriminatorValue("CE")
@Entity
public class CompteEpargne  extends CompteBancaire {

    private double Tauxinteret;
    
}
