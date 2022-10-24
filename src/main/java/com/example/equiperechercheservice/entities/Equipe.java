package com.example.equiperechercheservice.entities;


import com.example.equiperechercheservice.model.Axe;
import com.example.equiperechercheservice.model.Chercheur;
import com.example.equiperechercheservice.model.Labo;
import lombok.*;

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
    private List<Axe> axes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "Equipe_member",
            joinColumns = { @JoinColumn(name = "equipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "member_id") })
    private List<Chercheur> member = new ArrayList<>();

    @Transient
    private Labo labo;
    private Long laboID;



    //********************************************
    public void addAxe( Axe axe){
        this.axes.add(axe);
    }
    public List<Axe> getAxes(){return this.axes;}

    public Equipe(Long id,String acro_equipe, String intitule, Long responsableId ) {
        this.acro_equipe = acro_equipe;
        this.intitule = intitule;
        this.responsableId = responsableId;
        this.id=id;

    }

    public void addMember(Chercheur chercheur) {
        this.member.add(chercheur);
    }

    public void removeAxe(long axeId) {
        Axe axe = this.axes.stream().filter(t -> t.getId() == axeId).findFirst().orElse(null);
        if (axe != null) {
            this.axes.remove(axe);
            axe.getEquipe_list().remove(this);
        }
    }
}
