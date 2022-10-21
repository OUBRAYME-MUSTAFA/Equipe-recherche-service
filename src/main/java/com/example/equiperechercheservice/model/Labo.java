package com.example.equiperechercheservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Transient;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Labo {
                @Id
    private Long id;
    private String acro_labo;
    private String intitule;
    private Long ResponsableId;
    @Transient
    private Chercheur responsable;

}
