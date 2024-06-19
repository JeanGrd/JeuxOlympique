package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant une délégation.
 * Une délégation peut participer à plusieurs épreuves et avoir plusieurs participants.
 */
@Getter
@Setter
@Entity
public class Delegation {

    /**
     * L'identifiant unique de la délégation.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Le nom de la délégation.
     */
    private String nom;

    /**
     * Le nombre de médailles d'or remportées par la délégation.
     */
    private int nb_medaille_or;

    /**
     * Le nombre de médailles d'argent remportées par la délégation.
     */
    private int nb_medaille_argent;

    /**
     * Le nombre de médailles de bronze remportées par la délégation.
     */
    private int nb_medaille_bronze;

    /**
     * La liste des participations de la délégation aux épreuves.
     * La relation est en cascade et les participations orphelines seront supprimées.
     */
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participe> participes;

    /**
     * La liste des participants appartenant à la délégation.
     * La relation est en cascade et les participants orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
}
