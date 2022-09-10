package com.example.equiperechercheservice.web;

import com.example.equiperechercheservice.entities.Axe;
import com.example.equiperechercheservice.entities.Equipe;
import com.example.equiperechercheservice.repository.AxeRepository;
import com.example.equiperechercheservice.repository.EquipeRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class EquipeRestController {


    private AxeRepository axeRepository;
    private EquipeRepository equipeRepository;

    public EquipeRestController(EquipeRepository equipeRepository , AxeRepository axeRepository) {
        this.equipeRepository = equipeRepository;
        this.axeRepository = axeRepository;

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

        return equipeRepository.save(equipe);

    }

    @PutMapping("addAxe/{id}")
    public Equipe addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Equipe equipe = equipeRepository.findById(id).get();
        //Axe newAxe = axeRepository.findById(axe.getId()).get()

        Long IdAxe = axeRepository.findByName(axe.getName()).getId();
        Axe newAxe = axeRepository.findById(IdAxe).get();

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

