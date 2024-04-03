package com.example.emailsApp;


import java.sql.Date;
import java.util.UUID;
import java.util.stream.Stream;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import com.example.emailsApp.entity.Client;
import com.example.emailsApp.entity.CompteCourant;
import com.example.emailsApp.entity.CompteEpargne;
import com.example.emailsApp.entity.CompteStatus;
import com.example.emailsApp.entity.Operation;
import com.example.emailsApp.entity.OpetationType;
import com.example.emailsApp.repository.ClientRepo;
import com.example.emailsApp.repository.CompteRepo;
import com.example.emailsApp.repository.OperationRepo;

@SpringBootApplication
public class EmailsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailsAppApplication.class, args);
	}

 
    //@Bean
	/* CommandLineRunner start(CompteBancaireServiceImpl compteBancaireServiceImpl){
		return args->{

			Stream.of("ifra","med","hassan","youssef","youssra").forEach(name->{
				Client client= new Client();
				client.setFirstname(name);
				client.setEmails(name+"gmail.com");
				compteBancaireServiceImpl.saveClient(client);
			});
			compteBancaireServiceImpl.listClient().forEach(client->{
				try {
					compteBancaireServiceImpl.saveComptecourantBancaire(Math.random()*10000, 1000, client.getId());
					compteBancaireServiceImpl.saveCompteepargneBancaire(Math.random()*5000,4.5 , client.getId());
				} catch (ClientNotFoundExceptions e) {
					
					e.printStackTrace();
				}
			});
		};
	}
*/


  //@Bean
  CommandLineRunner start(ClientRepo clientRepo,
  CompteRepo compteRepo,
  OperationRepo operationRepo){

	return args -> {
		Stream.of("ifra","med","hassan","youssef","youssra").forEach(name ->{

			Client client = new Client();
			client.setFirstname(name);
			client.setLastname(name);
			client.setEmails(name +"gmail.com");
            clientRepo.save(client);
		});

		clientRepo.findAll().forEach(cl->{
			CompteCourant compteCourant= new CompteCourant();
			compteCourant.setId(UUID.randomUUID().toString());
			compteCourant.setSolde(Math.random()*1583930333);
			compteCourant.setDatecreation(new java.sql.Date(10));
			compteCourant.setStatus(CompteStatus.CREATED);
			compteCourant.setDecouvert(1000);
			compteCourant.setClient(cl);
            compteRepo.save(compteCourant);
			
			


			CompteEpargne compteEpargne= new CompteEpargne();
			compteEpargne.setId(UUID.randomUUID().toString());
			compteEpargne.setSolde(Math.random()*10000);
			compteEpargne.setDatecreation(new java.sql.Date(10));
			compteEpargne.setStatus(CompteStatus.CREATED);
			compteEpargne.setTauxinteret(4.5);
			compteEpargne.setClient(cl);
			compteRepo.save(compteEpargne);
			


			/* Operation operation = new Operation();
			operation.setMontant(Math.random()*100);
			operation.setType(OpetationType.Debit);
			operation.setDate(new Date(10));
			operation.setId(UUID.randomUUID().node());
			operation.setCompteBancaire(compteCourant);
			operationRepo.save(operation);*/
			
		});

		compteRepo.findAll().forEach(acc->{
			for(int i=0 ;  i < 4;i++){

				Operation operation = new Operation();
			    operation.setMontant(Math.random()*100);
				operation.setType(OpetationType.Debit);
			    operation.setDate(new Date(10));
				operation.setCompteBancaire(acc);
			operationRepo.save(operation);


			}
		});
		
	};
  }


}
