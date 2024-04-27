package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Billet extends Entite {

    @Id
    @GeneratedValue
    private long id;

    private long epreuveId;
    private long spectateurId;
    private double prix;
    private String etat;

}
