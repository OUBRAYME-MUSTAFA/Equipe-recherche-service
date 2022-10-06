package com.example.equiperechercheservice.feign;

import com.example.equiperechercheservice.model.Chercheur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CHERCHEUR-SERVICE")
public interface ChercheurRestCient {
    @GetMapping(path = "/chercheurs/{id}")
    public Chercheur getChercheurById(@PathVariable("id")Long id);

    @GetMapping(path = "/getChercheur/{name}")
    Chercheur getChercheurByName(@PathVariable("name")String name);
    //@GetMapping(path = "/chercheur/{id}")
    //public List<Chercheur> getChercheurById(@PathVariable("id")Long id);
}