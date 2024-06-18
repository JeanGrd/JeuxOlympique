package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Billet {

    @Id
    @GeneratedValue
    private long billetId;
    private double prix;
    private double remboursement;

    /* 3 états possibles :
        - Réservé
        - Annulé
        - Payé
    */
    private String etat;
    @ManyToOne
    @JoinColumn(name = "spectateur_email")
    private Spectateur spectateur;
    @ManyToOne
    @JoinColumn(name = "epreuveId")
    private Epreuve epreuve;

}
