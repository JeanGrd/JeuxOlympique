package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Billet {

    @Id
    @GeneratedValue
    private long billet_id;
    private double prix;

    /* 2 états possibles :
        - Réservé
        - Validé
    */
    private String etat;
    @ManyToOne
    @JoinColumn(name = "spectateur_id")
    private Spectateur spectateur;
    @ManyToOne
    @JoinColumn(name = "epreuve_id")
    private Epreuve epreuve;

}
