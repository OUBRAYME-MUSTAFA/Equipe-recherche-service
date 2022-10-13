package com.example.equiperechercheservice.entities;


import com.example.equiperechercheservice.model.Axe;
import com.example.equiperechercheservice.model.Chercheur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acro_equipe;
    private String intitule;
    private Long responsableId;
    @Transient
    private Chercheur responsable;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "equipes_axes",
            joinColumns = { @JoinColumn(name = "equipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "axe_id") })
    private List<Axe> axe_list = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "Equipe_member",
            joinColumns = { @JoinColumn(name = "equipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "member_id") })
    private List<Chercheur> Member = new ArrayList<>();




    //********************************************
    public void addAxe( Axe axe){
        this.axe_list.add(axe);
    }
    public List<Axe> getAxes(){return this.axe_list;}

    public Equipe(Long id,String acro_equipe, String intitule, Long responsableId) {
        this.acro_equipe = acro_equipe;
        this.intitule = intitule;
        this.responsableId = responsableId;
        this.id=id;
    }

    public void addMember(Chercheur chercheur) {
        this.Member.add(chercheur);
    }
}
