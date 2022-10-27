package com.example.equiperechercheservice.web;

import com.example.equiperechercheservice.feign.AxeRestClient;
import com.example.equiperechercheservice.model.Axe;
import com.example.equiperechercheservice.entities.Equipe;
import com.example.equiperechercheservice.model.Chercheur;
import com.example.equiperechercheservice.model.Labo;
import com.example.equiperechercheservice.repository.EquipeRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.equiperechercheservice.feign.ChercheurRestClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableFeignClients
@CrossOrigin("http://localhost:4200/")
public class EquipeRestController {


    private EquipeRepository equipeRepository;

    private ChercheurRestClient chercheurRestClient;
    private AxeRestClient axeRestClient;


    public EquipeRestController(EquipeRepository equipeRepository, ChercheurRestClient chercheurRestClient, AxeRestClient axeRestClient) {
        this.equipeRepository = equipeRepository;
        this.chercheurRestClient = chercheurRestClient;
        this.axeRestClient = axeRestClient;
    }

    @GetMapping(path = "/equipes/{id}")
    public Equipe getEquipeById(@PathVariable(name = "id") Long  id){
        Equipe equipe = equipeRepository.findById(id).get();
        equipe.setResponsable(chercheurRestClient.getChercheurById(equipe.getResponsableId()));
        //Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        //equipe.setCustomer(customer);
        equipe.getAxes().forEach(pi->{

            pi.setEquipe_list(null);
        });
        equipe.getMember().forEach(pi->{
            pi.setEquipes(null);
        });

        return equipe;
    }

    @GetMapping(path = "/fullEquipes")
    public List<Equipe> getFullEquipe() {

        List<Equipe> list__ = equipeRepository.findAll();
        list__.forEach(equipe -> {
            equipe.setResponsable(chercheurRestClient.getChercheurById(equipe.getResponsableId()));
            equipe.getAxes().forEach(pi->{
//                try {
//                    Axe axe = axeRestClient.getAxeById(pi.getId());
//                    System.out.println("***********************  AXE =  "+axe);
//                    pi.setAxeName(axe.getName());
//                    pi.setEquipe_list(null);
//                }catch (Exception e){
//                    pi.setEquipe_list(null);
//                    System.out.println("***********************    Hi 11");
//                    equipe.getAxes().remove(pi);
//                    //pi.getEquipe_list().remove(equipe);
//                    System.out.println("***********************    Hi 22");
//                    equipeRepository.save(equipe);
//                    System.out.println("***********************    Hi 33");
//                }
                pi.setEquipe_list(null);
            });
            equipe.getMember().forEach(pi->{
                        pi.setEquipes(null);
                    });
            if (equipe.getLaboID() != null ){

                Labo lb =axeRestClient.getFullLabo(equipe.getLaboID());
                if(lb.getEquipes_ID().contains(equipe.getId()))
                equipe.setLabo(lb);
            }



        });
        return list__;
    }

    @GetMapping(path = "/pureEquipes")
    public List<Equipe> getPureEquipe()
    {
        List<Equipe> list__ = new ArrayList<>();
        equipeRepository.findAll().forEach(equipe -> {
            if(equipe.getLaboID() == null)
                list__.add(getEquipeById(equipe.getId()));
        });
        return list__;
    }


    @PostMapping("/addEquipe")
    public ResponseEntity<Equipe> addEquipe(@RequestBody Equipe equipe){
        Chercheur chercheur = chercheurRestClient.getChercheurById(equipe.getResponsable().getId());
        Equipe equipe1 =new Equipe(equipe.getId(),equipe.getAcro_equipe(), equipe.getIntitule(),chercheur.getId());
        if(equipe.getLabo() != null)
        { equipe1.setLaboID(equipe.getLabo().getId());}
        equipeRepository.save(equipe1);
        equipe.getAxes().forEach(pi->{
            Axe newAxe = axeRestClient.getAxeById(pi.getId());
            System.out.println("***********id axe = "+newAxe.getId() +" || name = "+newAxe.getName());
            addAxe(newAxe,equipe1.getId());

        });
        equipe.getMember().forEach(member->{

            Chercheur newChercheur =chercheurRestClient.getChercheurById(member.getId());
            addMember(newChercheur,equipe1.getId());
        });

        return   new ResponseEntity<>(equipe1, HttpStatus.CREATED);
    }

//    @PostMapping("/addEquipe")
//    public ResponseEntity<Equipe> addEquipe(@RequestBody Equipe equipe){
//        equipe.setResponsableId(equipe.getResponsable().getId());
//        System.out.print(equipe);
//        return new ResponseEntity<>(equipeRepository.save(equipe), HttpStatus.CREATED);
//    }

    @PutMapping("equipe/addAxe/{id}")
    public void addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Equipe equipe =  equipeRepository.findById(id).get();
        equipe.addAxe(axe);
        System.out.println("********************** id = "+axe.getId()+"was ADded");
        equipeRepository.save(equipe);

    }

    @PutMapping("equipe/addMember/{id}")
    public Equipe addMember(@RequestBody Chercheur chercheur, @PathVariable long id) {
        Equipe equipe =  equipeRepository.findById(id).get();
        equipe.addMember(chercheur);
        equipeRepository.save(equipe);
        return equipe;
    }

    @PutMapping("update")
    public ResponseEntity<Equipe> updateEquipe(@RequestBody Equipe equipe){

        return addEquipe(equipe);

    }

    @PutMapping("addLabo/{id}")
    public void addLabo(@RequestBody Labo labo, @PathVariable long id) {
        Equipe equipe = equipeRepository.findById(id).get();
        //Axe newAxe = axeRepository.findById(axe.getId()).get()

        equipe.setLaboID(labo.getId());

        equipeRepository.save(equipe);

    }
    @PutMapping("deleteLabo/{code}")
    void deleteLabo(@PathVariable  long code){
       Equipe ep = equipeRepository.findById(code).get();

       ep.setLaboID(null);
        equipeRepository.save(ep);

    }
    @DeleteMapping(path = "/{code}")
    public void deleteEquipe(@PathVariable (name = "code") long code ) {

        Equipe equipe = equipeRepository.findById(code).get();
        if (equipe.getLaboID() != null)
            axeRestClient.deleteEquipe(equipe.getLaboID() , code);

        equipeRepository.deleteById(code);
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

