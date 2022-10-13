package com.example.equiperechercheservice.model;

import com.example.equiperechercheservice.entities.Equipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Axe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "axe_list")
    @JsonIgnore
    private Set<Equipe> equipe_list = new HashSet<>();

    public void setAxeName(String name) {
        this.name = name;
    }

    public void addEquipe( Equipe equipe){
        equipe_list.add(equipe);
    }
    public Axe(String name){
        this.name= name;
    }


}
