// InfrastructureSportive.java
package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant une infrastructure sportive.
 * Une infrastructure sportive peut accueillir plusieurs épreuves.
 */
@Getter
@Setter
@Entity
public class InfrastructureSportive {

    /**
     * L'identifiant unique de l'infrastructure sportive.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Le nom de l'infrastructure sportive.
     */
    private String nom;

    /**
     * L'adresse de l'infrastructure sportive.
     */
    private String adresse;

    /**
     * La capacité d'accueil de l'infrastructure sportive.
     */
    private int capacite;

    /**
     * La liste des épreuves accueillies par l'infrastructure sportive.
     * La relation est en cascade et les épreuves orphelines seront supprimées.
     */
    @OneToMany(mappedBy = "infrastructureSportive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Epreuve> epreuves;
}