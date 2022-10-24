package com.example.equiperechercheservice.feign;

import com.example.equiperechercheservice.entities.Equipe;
import com.example.equiperechercheservice.model.Axe;
import com.example.equiperechercheservice.model.Labo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LABO-SERVICE")
public interface AxeRestClient {
    @GetMapping(path = "/axes/{id}")
    public Axe getAxeById(@PathVariable("id")Long id);

    @GetMapping(path = "/getAxes/{name}")
    Axe getAxeByName(@PathVariable("name")String name);

    @GetMapping(path = "/fullLabo/{id}")
    Labo getFullLabo(@PathVariable(name = "id") Long id);

    @PutMapping("addEquipe/{id}")
    public Labo addEquipe(@RequestBody Equipe equipe, @PathVariable long id);

    @PutMapping(path = "/labos/{id}/deleteEquipe/{code}")
    public void deleteEquipe(@PathVariable (name = "id") long id , @PathVariable (name = "code") long code );

}
