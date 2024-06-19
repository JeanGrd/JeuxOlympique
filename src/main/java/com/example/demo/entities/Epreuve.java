package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Entité représentant une épreuve sportive.
 * Une épreuve peut avoir plusieurs billets, résultats et participations associées.
 */
@Getter
@Setter
@Entity
public class Epreuve {

    /**
     * L'identifiant unique de l'épreuve.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Le nom de l'épreuve.
     */
    private String nom;

    /**
     * La date de l'épreuve.
     */
    private LocalDate date;

    /**
     * Le nombre maximum de délégations participant à l'épreuve.
     */
    private int nb_delegations;

    /**
     * Le nombre de billets disponibles pour l'épreuve.
     */
    private int nb_billets;

    /**
     * Le prix du billet pour l'épreuve.
     */
    private double prix;

    /**
     * L'infrastructure sportive qui accueille l'épreuve.
     */
    @ManyToOne
    @JoinColumn(name = "idInfrastructure")
    private InfrastructureSportive infrastructureSportive;

    /**
     * La liste des billets pour l'épreuve.
     * La relation est en cascade et les billets orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "epreuve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Billet> billets;

    /**
     * La liste des résultats pour l'épreuve.
     * La relation est en cascade et les résultats orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "epreuve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultat> resultats;

    /**
     * La liste des participations des délégations à l'épreuve.
     * La relation est en cascade et les participations orphelines seront supprimées.
     */
    @OneToMany(mappedBy = "epreuve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participe> participes;
}
