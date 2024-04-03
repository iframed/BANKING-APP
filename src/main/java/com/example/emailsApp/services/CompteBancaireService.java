package com.example.emailsApp.services;

import java.util.List;

import com.example.emailsApp.Exceptions.ClientNotFoundExceptions;
import com.example.emailsApp.Exceptions.SoldeInsufisant;
import com.example.emailsApp.dto.ClientDto;
import com.example.emailsApp.dto.CompteBancaireDto;
import com.example.emailsApp.dto.CompteCourantDto;
import com.example.emailsApp.dto.CompteEpargneDto;
import com.example.emailsApp.dto.OperationDto;
import com.example.emailsApp.dto.OperationPageDtos;


public interface CompteBancaireService {
    

    //double solde, double decouvert, long id
ClientDto saveClient(ClientDto client);

CompteCourantDto saveComptecourantBancaire(double solde, double decouvert, long id) throws ClientNotFoundExceptions;
CompteEpargneDto saveCompteepargneBancaire(double solde, double Tauxinteret, long id) throws ClientNotFoundExceptions;

List<ClientDto> listClient();

List<ClientDto> searchClient(String keyword);

ClientDto updateClient(ClientDto clientDto);

void deleteClient(long id);

CompteBancaireDto getCompteBancaire(String id);

ClientDto getClientDto(long id) throws ClientNotFoundExceptions;

void debiter(String id, double montant , String description) throws SoldeInsufisant;


void crediter(String id, double montant,String description);


void transferer(String compteIdSource, String compteIdDestinataire, double montant) throws SoldeInsufisant;

List<CompteBancaireDto> compteBancairelList();


List<OperationDto> historiqueCompte(String id);


OperationPageDtos getCompteHistorique(String id, int page, int size);


}
