package com.example.emailsApp.dto;


import java.util.Date;

import com.example.emailsApp.entity.CompteStatus;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Data
@EqualsAndHashCode(callSuper=true)
@Getter
@Setter
public class CompteEpargneDto extends CompteBancaireDto{
       
   
    private String id ;
    
    private Date datecreation;

    private  double solde ;
 
    private CompteStatus Status ;

    private ClientDto clientDto;

    private double tauxinteret;

}
