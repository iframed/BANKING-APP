package com.example.emailsApp.entity;



import java.util.Date;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;


import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name ="CompteBancaire")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)

public abstract class CompteBancaire {

    @Id
    @UuidGenerator
    private String id ;
    
    private Date datecreation;

    private  double solde ;
 
    @Enumerated(EnumType.STRING)
    private CompteStatus Status ;

    @ManyToOne
    @JoinColumn(name = "Clientid", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "compteBancaire",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Operation> operation;


}
