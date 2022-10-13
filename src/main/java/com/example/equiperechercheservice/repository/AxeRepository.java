package com.example.equiperechercheservice.repository;

import com.example.equiperechercheservice.model.Axe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AxeRepository extends JpaRepository<Axe, Long> {
    Axe getAxeById(Long axeID);

    Axe findByName(String name);

    Axe getFindByName(String name);
    //Collection<Axe> findById(Long id);
}
