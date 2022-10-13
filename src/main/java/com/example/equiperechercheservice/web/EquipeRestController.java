package com.example.equiperechercheservice.web;

import com.example.equiperechercheservice.feign.AxeRestClient;
import com.example.equiperechercheservice.model.Axe;
import com.example.equiperechercheservice.entities.Equipe;
import com.example.equiperechercheservice.model.Chercheur;
import com.example.equiperechercheservice.repository.AxeRepository;
import com.example.equiperechercheservice.repository.EquipeRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.equiperechercheservice.feign.ChercheurRestClient;

import java.util.List;

@RestController
@EnableFeignClients
public class EquipeRestController {


    private AxeRepository axeRepository;
    private EquipeRepository equipeRepository;

    private ChercheurRestClient chercheurRestClient;
    private AxeRestClient axeRestClient;

    public EquipeRestController(AxeRepository axeRepository,  AxeRestClient axeRestClient, EquipeRepository equipeRepository,ChercheurRestClient chercheurRestClient) {
        this.axeRepository = axeRepository;
        this.equipeRepository = equipeRepository;
        this.chercheurRestClient = chercheurRestClient;
        this.axeRestClient = axeRestClient;
    }

    @GetMapping(path = "/fullEquipe/{id}")
    public Equipe getEquipe(@PathVariable(name = "id") Long id){
        Equipe equipe = equipeRepository.findById(id).get();
        //Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        //equipe.setCustomer(customer);
        equipe.getAxe_list().forEach(pi->{
            Axe axe = axeRepository.getAxeById(pi.getId());
            //pi.setProduct(product);
            pi.setAxeName(axe.getName());
        });
        return equipe;
    }

    @GetMapping(path = "/fullEquipes")
    public List<Equipe> getFullEquipe() {

        List<Equipe> list__ = equipeRepository.findAll();
        list__.forEach(equipe -> {
            Chercheur chercheur = chercheurRestClient.getChercheurById(equipe.getResponsableId());
            equipe.setResponsable(chercheur);
            equipe.getAxes().forEach(pi->{
                Axe axe = axeRepository.getAxeById(pi.getId());
                pi.setAxeName(axe.getName());
                pi.setEquipe_list(null);
            });
            equipe.getMember().forEach(ch->{
                Chercheur chercheur1 = chercheurRestClient.getChercheurByName(ch.getName());
                ch.setChercheurName(chercheur1.getName());
                ch.setEquipes(null);
            });



        });
        return list__;
    }


    @PostMapping("/addEquipe")
    public ResponseEntity<Equipe> addEquipe(@RequestBody Equipe equipe){
        Chercheur chercheur = chercheurRestClient.getChercheurById(equipe.getResponsable().getId());
        Equipe equipe1 =new Equipe(equipe.getId(),equipe.getAcro_equipe(), equipe.getIntitule(),chercheur.getId());
        equipeRepository.save(equipe1);
        equipe.getAxe_list().forEach(pi->{
            addAxe(axeRepository.findById(pi.getId()).get(),equipe1.getId());

        });
        equipe.getMember().forEach(member->{
            Chercheur newChercheur =chercheurRestClient.getChercheurById(member.getId());
            System.out.println("********************** id = "+newChercheur.getId()+" // name = "+newChercheur.getName());
            addMember(newChercheur,equipe1.getId());
        });

        return   new ResponseEntity<>(equipe1, HttpStatus.CREATED);
    }

    @PutMapping("equipe/addAxe/{id}")
    public void addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Equipe equipe =  equipeRepository.findById(id).get();
        equipe.addAxe(axe);
        equipeRepository.save(equipe);

    }

    @PutMapping("equipe/addMember/{id}")
    public Equipe addMember(@RequestBody Chercheur chercheur, @PathVariable long id) {
        Equipe equipe =  equipeRepository.findById(id).get();
        equipe.addMember(chercheur);
        equipeRepository.save(equipe);
        return equipe;
    }

//    @PutMapping("addEquipe/{id}")
//    public Axe update(@RequestBody Equipe  equipe, @PathVariable long id) {
//        Axe axe = axeRepository.findById(id).get();
//
//        axe.getEquipe_list().add(equipe);
//
//        equipeRepository.save(equipe);
//        return axe;
//    }
}

