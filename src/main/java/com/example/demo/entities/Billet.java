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
    private long billet_id;
    private double prix;
    private String etat;
    @ManyToOne
    @JoinColumn(name = "spectateur_id")
    private Spectateur spectateur;
    @ManyToOne
    @JoinColumn(name = "epreuve_id")
    private Epreuve epreuve;

}
