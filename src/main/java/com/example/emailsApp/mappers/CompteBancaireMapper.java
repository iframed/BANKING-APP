package com.example.emailsApp.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.emailsApp.dto.ClientDto;
import com.example.emailsApp.dto.CompteCourantDto;
import com.example.emailsApp.dto.CompteEpargneDto;
import com.example.emailsApp.dto.OperationDto;
import com.example.emailsApp.entity.Client;
import com.example.emailsApp.entity.CompteCourant;
import com.example.emailsApp.entity.CompteEpargne;
import com.example.emailsApp.entity.Operation;


@Service
public class CompteBancaireMapper {
    
    public ClientDto fromClient(Client client){
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client, clientDto);
        return clientDto;
    }



    public Client toClientDto(ClientDto clientDto){
        Client client= new Client();
        BeanUtils.copyProperties(clientDto, client);
        return client;
        
    }


   public CompteCourantDto fromCompteCourant(CompteCourant compteCourant){

    CompteCourantDto compteCourantDto=new CompteCourantDto();
    BeanUtils.copyProperties(compteCourant, compteCourantDto);
    compteCourantDto.setClientDto(fromClient(compteCourant.getClient()));
    compteCourantDto.setType(compteCourant.getClass().getSimpleName());
    return compteCourantDto;

   }
   
   public CompteCourant fromCompteCourantDto(CompteCourantDto compteCourantDto){
    CompteCourant compteCourant= new CompteCourant();
    BeanUtils.copyProperties(compteCourantDto, compteCourant);
    compteCourant.setClient(toClientDto(compteCourantDto.getClientDto()));
    return compteCourant;
   }
 
   public CompteEpargneDto fromCompteE(CompteEpargne compteEpargne){
    CompteEpargneDto compteEpargneDto = new CompteEpargneDto();
    BeanUtils.copyProperties(compteEpargne, compteEpargneDto);
    compteEpargneDto.setClientDto(fromClient(compteEpargne.getClient()));
    compteEpargneDto.setType(compteEpargne.getClass().getSimpleName());
    return compteEpargneDto;
   }

   public CompteEpargne fromCompteEDto(CompteEpargneDto compteEpargneDto){
    CompteEpargne compteEpargne= new CompteEpargne();
    BeanUtils.copyProperties(compteEpargneDto, compteEpargne);
    compteEpargne.setClient(toClientDto(compteEpargneDto.getClientDto()));
    
    return compteEpargne;

}
 

   public OperationDto fromOper(Operation operation){
    OperationDto operationDto= new OperationDto();
    BeanUtils.copyProperties(operation, operationDto);
    return operationDto;
   }

  public Operation fromoperDto(OperationDto operationDto){
    Operation operation= new Operation();
    BeanUtils.copyProperties(operationDto, operation);
    return operation;
  }



}
