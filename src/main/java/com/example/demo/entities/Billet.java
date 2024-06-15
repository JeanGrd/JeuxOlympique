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

    /* 2 états possibles :
        - Réservé
        - Validé
    */
    private String etat;
    @ManyToOne
    @JoinColumn(name = "spectateurId")
    private Spectateur spectateur;
    @ManyToOne
    @JoinColumn(name = "epreuveId")
    private Epreuve epreuve;

}
