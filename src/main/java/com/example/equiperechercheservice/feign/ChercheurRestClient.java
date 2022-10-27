package com.example.equiperechercheservice.feign;

import com.example.equiperechercheservice.model.Chercheur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CHERCHEURS")
public interface ChercheurRestClient {
    @GetMapping(path = "/chercheurs/{id}")
    public Chercheur getChercheurById(@PathVariable("id")Long id);

//    @GetMapping(path = "/getChercheur/{name}")
//    Chercheur getChercheurByName(@PathVariable("name")String name);

    //Chercheur findById(Long id);
    //@GetMapping(path = "/chercheur/{id}")
    //public List<Chercheur> getChercheurById(@PathVariable("id")Long id);
}