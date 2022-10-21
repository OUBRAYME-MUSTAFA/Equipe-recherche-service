package com.example.equiperechercheservice.model;

import com.example.equiperechercheservice.entities.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chercheur {
    @Id
    private Long id ;
    private String name;
    private String role;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "Member")
    @JsonIgnore
    private List<Equipe> equipes;

    public void addEquipes(Equipe equipe) {
        this.equipes.add(equipe);
        equipe.getMember().add(this);
    }
    public void setChercheurName(String name) {
        this.name =name;
    }

}