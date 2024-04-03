package com.example.emailsApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emailsApp.Exceptions.ClientNotFoundExceptions;
import com.example.emailsApp.Exceptions.SoldeInsufisant;

import com.example.emailsApp.dto.CompteBancaireDto;
import com.example.emailsApp.dto.CompteCourantDto;
import com.example.emailsApp.dto.CompteEpargneDto;
import com.example.emailsApp.dto.CreditDto;
import com.example.emailsApp.dto.OperationDto;
import com.example.emailsApp.dto.OperationPageDtos;
import com.example.emailsApp.dto.TransfertDto;
import com.example.emailsApp.dto.debitDto;

import com.example.emailsApp.services.CompteBancaireService;







@RestController
@RequestMapping("/auth")
public class CompteBancaireController {

    
    
    @Autowired
    private CompteBancaireService compteBancaireService;
    

    public CompteBancaireController(CompteBancaireService compteBancaireService) {
        this.compteBancaireService = compteBancaireService;
    }

    @GetMapping("/compte/{id}")
    public CompteBancaireDto getCompte(@PathVariable String id){
        return compteBancaireService.getCompteBancaire(id);

    }
    

    @GetMapping("/compte/{id}/operations")
    public List<OperationDto> historique(String id){

        return compteBancaireService.historiqueCompte(id);


        
    }


    @GetMapping("/compte/{id}/pageOperations")
    public OperationPageDtos getHistorique(@PathVariable String id ,
                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "55") int size){

                                        return compteBancaireService.getCompteHistorique(id, page, size) ;           
        
    }

    @PostMapping("/compte/debit")
    public debitDto debit( @RequestBody debitDto debitDto) throws SoldeInsufisant{

    this.compteBancaireService.debiter(debitDto.getId(), debitDto.getMontant(), debitDto.getDescription());
    return debitDto;
    }
   
    @PostMapping("/compte/credit")
    public CreditDto credit(@RequestBody CreditDto creditDto) throws SoldeInsufisant{

    this.compteBancaireService.crediter(creditDto.getId(), creditDto.getMontant(), creditDto.getDescription());
    return creditDto;
    }


    @PostMapping("/compte/transfert")
    public TransfertDto transfert(@RequestBody TransfertDto transfertDto) throws SoldeInsufisant{

    this.compteBancaireService.transferer(transfertDto.getCompteDestinataire(), transfertDto.getCompteSource(), transfertDto.getMontant());
    return transfertDto;
    }


    @PostMapping("/compte/newcompte")
    public CompteCourantDto newCompteC(@RequestBody CompteCourantDto compteCourantDto) throws ClientNotFoundExceptions {
    
 


    
   
    
    // Sauvegarde du compte
    return this.compteBancaireService.saveComptecourantBancaire(compteCourantDto.getSolde(),compteCourantDto.getDecouvert(),compteCourantDto.getClientDto().getId());
    }


    @PostMapping("/compte/newcomptee")
    public CompteEpargneDto NewComptee(@RequestBody CompteEpargneDto compteEpargneDto) throws ClientNotFoundExceptions{
        return this.compteBancaireService.saveCompteepargneBancaire(compteEpargneDto.getSolde(), compteEpargneDto.getTauxinteret(), compteEpargneDto.getClientDto().getId());

    }


}
