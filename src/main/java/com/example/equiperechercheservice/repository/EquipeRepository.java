package com.example.equiperechercheservice.repository;

import com.example.equiperechercheservice.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
