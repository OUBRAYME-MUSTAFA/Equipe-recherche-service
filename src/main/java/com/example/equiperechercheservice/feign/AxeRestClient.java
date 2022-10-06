package com.example.equiperechercheservice.feign;

import com.example.equiperechercheservice.entities.Axe;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LABO-SERVICE")
public interface AxeRestClient {
    @GetMapping(path = "/axes/{id}")
    public Axe getAxesById(@PathVariable("id")Long id);

    @GetMapping(path = "/getAxes/{name}")
    Axe getAxeByName(@PathVariable("name")String name);
}
