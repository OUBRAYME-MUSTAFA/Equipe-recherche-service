package com.example.equiperechercheservice.web;

import com.example.equiperechercheservice.feign.AxeRestClient;
import com.example.equiperechercheservice.entities.Axe;
import com.example.equiperechercheservice.entities.Equipe;
import com.example.equiperechercheservice.repository.AxeRepository;
import com.example.equiperechercheservice.repository.EquipeRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;
import com.example.equiperechercheservice.feign.ChercheurRestCient;

@RestController
@EnableFeignClients
public class EquipeRestController {


    private AxeRepository axeRepository;
    private EquipeRepository equipeRepository;

    private ChercheurRestCient chercheurRestCient;
    private AxeRestClient axeRestClient;

    public EquipeRestController(AxeRepository axeRepository, EquipeRepository equipeRepository, ChercheurRestCient chercheurRestCient, AxeRestClient axeRestClient) {
        this.axeRepository = axeRepository;
        this.equipeRepository = equipeRepository;
        this.chercheurRestCient = chercheurRestCient;
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


    @PostMapping("addEquipe")
    public Equipe addEquipe(@RequestBody Equipe equipe){

       // equipe.setResponsable(chercheurRestCient.getChercheurByName(equipe.getResponsable().getName()));

        return equipeRepository.save(equipe);

    }

    @PutMapping("addAxe/{id}")
    public Equipe addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Equipe equipe = equipeRepository.findById(id).get();

        Axe newAxe = axeRepository.getFindByName(axe.getName());

        equipe.getAxe_list().add(newAxe);
        newAxe.getEquipe_list().add(equipe);


        equipeRepository.save(equipe);
        axeRepository.save(newAxe);
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

