package com.example.emailsApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emailsApp.Exceptions.ClientNotFoundExceptions;
import com.example.emailsApp.dto.ClientDto;

import com.example.emailsApp.services.CompteBancaireService;


import lombok.AllArgsConstructor;




@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class ClientController {
    
    @Autowired
    private CompteBancaireService compteBancaireService;

    @GetMapping("/clients")
    @PreAuthorize("hasAuthority('ADMIN')")

    //@RolesAllowed({"USER"})
    public List<ClientDto> client(){
        return compteBancaireService.listClient();
    }
    @GetMapping("/clients/search")
    @PreAuthorize("hasAuthority('ADMIN')")

    //@RolesAllowed({"USER"})
    public List<ClientDto> searchClient(@RequestParam(name = "keyword",defaultValue ="") String keyword){
        return compteBancaireService.searchClient( "%"+keyword+"%");
    }

    @GetMapping("/clientid/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")

    //@RolesAllowed({"USER"})
    public ClientDto getClientdto(@PathVariable(name ="id") long id) throws ClientNotFoundExceptions{
        return  compteBancaireService.getClientDto(id);
}

   
  @PostMapping("/ajouterclient")
  @PreAuthorize("hasAuthority('ADMIN')")

  //@RolesAllowed({"ADMIN"})
   public ClientDto ajouterClient(@RequestBody ClientDto clientDto){

    return compteBancaireService.saveClient(clientDto);

   }
 //cccc
  @PutMapping("/clients/{id}")
  
  //@RolesAllowed({"ADMIN"})
  public ClientDto updateClient(@PathVariable long id, @RequestBody ClientDto clientDto)
   {

    clientDto.setId(id);
    return compteBancaireService.updateClient(clientDto);
   }

   
   @DeleteMapping("/clients/{id}")
   public void deleteClient(@PathVariable long id)
    {
       compteBancaireService.deleteClient(id);
    }






}
