package com.example.emailsApp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name ="Client")

public class Client {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id ;

private String firstname;

private String lastname;

private String emails;

private double telephone;



@OneToMany(mappedBy = "client" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
private List<CompteBancaire> CompteBancaire;

}
