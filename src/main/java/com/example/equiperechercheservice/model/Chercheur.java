package com.example.equiperechercheservice.model;

import com.example.equiperechercheservice.entities.Equipe;
import com.sun.org.apache.bcel.internal.generic.RETURN;
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
    private String nom;
    private String prenom ;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "member")
    @JsonIgnore
    private List<Equipe> equipes;

    public void addEquipes(Equipe equipe) {
        this.equipes.add(equipe);
        equipe.getMember().add(this);
    }
    public void setChercheurName(String name) {
        this.nom =name;
    }

    public long getId(Chercheur chercheur) {
        return this.id;
    }
}