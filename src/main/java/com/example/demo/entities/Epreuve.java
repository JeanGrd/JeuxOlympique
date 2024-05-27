package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Epreuve {

    @Id
    @GeneratedValue
    private long epreuve_id;
    private String nom;
    private LocalDate date;
    private int nb_delegations;
    private int nb_billets;
    private String etat;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "infrastructure_id")
    private InfrastructureSportive infrastructureSportive;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "epreuve_delegation",
            joinColumns = @JoinColumn(name = "epreuve_id"),
            inverseJoinColumns = @JoinColumn(name = "delegation_id")
    )
    private Set<Delegation> delegations = new HashSet<>();

}
