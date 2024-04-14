package com.example.emailsApp.ServiceImpl;



import java.util.Date;
import java.util.List;

import java.util.UUID;

import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.emailsApp.Exceptions.ClientNotFoundExceptions;
import com.example.emailsApp.Exceptions.SoldeInsufisant;
import com.example.emailsApp.dto.ClientDto;
import com.example.emailsApp.dto.CompteBancaireDto;
import com.example.emailsApp.dto.CompteCourantDto;
import com.example.emailsApp.dto.CompteEpargneDto;
import com.example.emailsApp.dto.OperationDto;
import com.example.emailsApp.dto.OperationPageDtos;
import com.example.emailsApp.entity.Client;
import com.example.emailsApp.entity.CompteBancaire;
import com.example.emailsApp.entity.CompteCourant;
import com.example.emailsApp.entity.CompteEpargne;
import com.example.emailsApp.entity.Operation;
import com.example.emailsApp.entity.OpetationType;
import com.example.emailsApp.mappers.CompteBancaireMapper;
import com.example.emailsApp.repository.ClientRepo;
import com.example.emailsApp.repository.CompteRepo;
import com.example.emailsApp.repository.OperationRepo;
import com.example.emailsApp.services.CompteBancaireService;








@Service
 @Transactional


 public class CompteBancaireServiceImpl implements CompteBancaireService {
   //utilisation des repo 
    private CompteRepo compteRepo;
    private OperationRepo operationRepo;
    private ClientRepo clientRepo;
    private CompteBancaireMapper mapper;


 //injectiondesdepandances via constructeur
    public CompteBancaireServiceImpl(CompteRepo compteRepo, OperationRepo operationRepo, ClientRepo clientRepo, CompteBancaireMapper mapper) {
        this.compteRepo = compteRepo;
        this.operationRepo = operationRepo;
        this.clientRepo = clientRepo;
        this.mapper= mapper;
    }
    
    @Override

    public ClientDto saveClient(ClientDto clientDto) {
        
       Client client=mapper.toClientDto(clientDto);
        Client saveClient = clientRepo.save(client);
        return mapper.fromClient(saveClient);
    }
    

    @Override
    public CompteCourantDto saveComptecourantBancaire(double solde, double decouvert, long id) throws ClientNotFoundExceptions {
        
      

         Client client=clientRepo.findById(id).orElse(null);
        
        if(client==null)
        throw new ClientNotFoundExceptions("notfound");
    

      

       CompteCourant compteCourant;
        

            compteCourant = new CompteCourant();
            compteCourant.setId(UUID.randomUUID().toString());
            compteCourant.setDatecreation(new Date(0));
            compteCourant.setSolde(solde);
            compteCourant.setClient(client);
            compteCourant.setDecouvert(decouvert);
            
            CompteCourant saveCompte = compteRepo.save(compteCourant);
            
             return mapper.fromCompteCourant(saveCompte);

    }
        
    @Override
    public CompteEpargneDto saveCompteepargneBancaire(double solde, double Tauxinteret, long id) throws ClientNotFoundExceptions
       {    
        Client client=clientRepo.findById(id).orElse(null);
        
        if(client==null)
        throw new ClientNotFoundExceptions("notfound");

       CompteEpargne compteEpargne;
        
       compteEpargne = new CompteEpargne();

       compteEpargne.setDatecreation(new Date());
       compteEpargne.setSolde(solde);
       compteEpargne.setClient(client);
       compteEpargne.setTauxinteret(Tauxinteret);
       
            
            CompteEpargne saveCompteE = compteRepo.save(compteEpargne);
            
             return mapper.fromCompteE(saveCompteE);
               
       

    
}

    @Override
    public List<ClientDto> listClient() {
      List<Client> clients = clientRepo.findAll();
      List<ClientDto> clientDtos= clients.stream().map(cl->mapper.fromClient(cl)).collect(Collectors.toList());
      return clientDtos;
       
       
    }

    @Override
    public CompteBancaireDto getCompteBancaire(String id) {
       CompteBancaire compteBancaire = compteRepo.findById(id).orElse(null);
       if(compteBancaire instanceof CompteEpargne){
        CompteEpargne compteEpargne= (CompteEpargne) compteBancaire;
        return mapper.fromCompteE(compteEpargne);
       }else{
        CompteCourant compteCourant= (CompteCourant) compteBancaire;

        return mapper.fromCompteCourant(compteCourant);
       }
    }

    @Override
    public void debiter(String id, double montant, String description) throws SoldeInsufisant {

      

    CompteBancaire compteBancaire = compteRepo.findById(id).orElse(null);
     if(compteBancaire.getSolde()<montant)
     throw new SoldeInsufisant("insuffisant");
      
 
   
       Operation operation=new Operation();
       operation.setType(OpetationType.Debit);
       operation.setMontant(montant);
       operation.setDescription(description);
       operation.setDate(new Date());
       operation.setCompteBancaire(compteBancaire);
       operationRepo.save(operation);
       compteBancaire.setSolde(compteBancaire.getSolde()-montant);
       compteRepo.save(compteBancaire);

    }

    @Override
    public void crediter(String id, double montant, String description) {
      CompteBancaire compteBancaire = compteRepo.findById(id).orElse(null);
       Operation operation=new Operation();
       operation.setType(OpetationType.Credit);
       operation.setMontant(montant);
       operation.setDescription(description);
       operation.setDate( new Date( ));
       
       operation.setCompteBancaire(compteBancaire);
       operationRepo.save(operation);
       //compteBancaire.setSolde(compteBancaire.getSolde()+ montant);
       double nouveauSolde = compteBancaire.getSolde() + montant;
       compteBancaire.setSolde(nouveauSolde);

       compteRepo.save(compteBancaire);
    }

    @Override
    public void transferer(String compteIdSource, String compteIdDestinataire, double montant) throws SoldeInsufisant {
        
     debiter(compteIdSource, montant, "transfert a"+compteIdDestinataire);

     crediter(compteIdDestinataire, montant, "transfert de"+compteIdSource);


        

    }

   @Override
    public List<CompteBancaireDto> compteBancairelList(){

        List<CompteBancaire> compteBancaires= compteRepo.findAll();
        List<CompteBancaireDto> compteBancaireDtos =compteBancaires.stream().map(compteBancaire -> {
          if(compteBancaire instanceof CompteCourant){
            CompteCourant compteCourant= (CompteCourant) compteBancaire;
            return mapper.fromCompteCourant(compteCourant);
          }else{
            CompteEpargne compteEpargne= (CompteEpargne) compteBancaire;
            return mapper.fromCompteE(compteEpargne);
          }
        }).collect(Collectors.toList());
        return compteBancaireDtos;
    }

    
    

    @Override
    public ClientDto getClientDto(long id) throws ClientNotFoundExceptions {
      Client client= clientRepo.findById(id).orElseThrow(()->new ClientNotFoundExceptions("not found"));
      return mapper.fromClient(client) ;
    }

    

   

    @Override
    public ClientDto updateClient(ClientDto clientDto) {
      Client client=mapper.toClientDto(clientDto);
      Client saveClient =clientRepo.save(client);
      return mapper.fromClient(saveClient);
    }
    


    @Override
    public void deleteClient(long id){
      clientRepo.deleteById(id);
    }

    @Override
    public List<OperationDto> historiqueCompte(String id){

      List<Operation> operation= operationRepo.findByCompteBancaireId(id);
     
      return operation.stream().map(op->mapper.fromOper(op)).collect(Collectors.toList());

      
    }

    @Override
    public OperationPageDtos getCompteHistorique(String id, int page, int size) {
      
      CompteBancaire compteBancaire= compteRepo.findById(id).orElse(null);

      // Créez un objet Pageable pour la pagination
      Pageable pageable = PageRequest.of(page, size);

      Page<Operation> operation= operationRepo.findByCompteBancaireIdOrderByDateDesc(id, pageable);

      OperationPageDtos operationPageDtos= new OperationPageDtos();
      List<OperationDto> operationDtos= operation.getContent().stream().map(op->mapper.fromOper(op)).collect(Collectors.toList());
      operationPageDtos.setOperationDto(operationDtos);
      operationPageDtos.setId(compteBancaire.getId());
      operationPageDtos.setDatecreation(compteBancaire.getDatecreation());
      operationPageDtos.setStatus(compteBancaire.getStatus());
      
     
      operationPageDtos.setSolde(compteBancaire.getSolde());
      operationPageDtos.setPagesize(size);
      operationPageDtos.setPageactuelle(page);
      operationPageDtos.setTotalepages(operation.getTotalPages());

      return operationPageDtos;




    }

    @Override
    public List<ClientDto> searchClient(String keyword) {

     List<Client> client= clientRepo.searchClients(keyword);
     List<ClientDto> clientDtos = client.stream().map(cl -> mapper.fromClient(cl)).collect(Collectors.toList());

      return clientDtos;
     
    }

  
    


 








  }