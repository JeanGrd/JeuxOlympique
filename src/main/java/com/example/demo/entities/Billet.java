package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un billet pour une épreuve.
 * Un billet est associé à un spectateur et une épreuve.
 */
@Getter
@Setter
@Entity
public class Billet {

    /**
     * L'identifiant unique du billet.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Le prix du billet.
     */
    private double prix;

    /**
     * Le montant du remboursement du billet.
     */
    private double remboursement;

    /**
     * L'état du billet.
     * 3 états possibles :
     * - Réservé
     * - Annulé
     * - Payé
     */
    private String etat;

    /**
     * Le spectateur associé à ce billet.
     */
    @ManyToOne
    @JoinColumn(name = "spectateur_email")
    @JsonIgnore
    private Spectateur spectateur;

    /**
     * L'épreuve associée à ce billet.
     */
    @ManyToOne
    @JoinColumn(name = "idEpreuve")
    @JsonIgnore
    private Epreuve epreuve;

}
