package com.example.equiperechercheservice.entities;


import com.example.equiperechercheservice.model.Chercheur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acro_equipe;
    private String intitule;
    @Transient
    private Chercheur responsable;
    @Transient
    private List<Axe> axe_list = new ArrayList<Axe>();

    public void addAxe( Axe axe){
        axe_list.add(axe);
    }

    public Equipe(String acro_equipe, String intitule, Chercheur responsable) {
        this.acro_equipe = acro_equipe;
        this.intitule = intitule;
        this.responsable = responsable;
    }
}
