package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Epreuve {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long epreuveId;

    private String nom;
    private LocalDate date;
    private int nb_delegations;
    private int nb_billets;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "infrastructureId")
    private InfrastructureSportive infrastructureSportive;

}
