package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Epreuve {

    @Id
    @GeneratedValue
    private long epreuve_id;
    private String nom;
    private Date date;
    private int nb_delegations;
    private int nb_billets;
    private String etat;

    @ManyToOne
    @JoinColumn(name = "infrastructure_id")
    private InfrastructureSportive infrastructureSportive;

    @OneToMany
    @JoinTable(
            name = "epreuve_delegation",
            joinColumns = @JoinColumn(name = "epreuve_id"),
            inverseJoinColumns = @JoinColumn(name = "delegation_id")
    )
    private Set<Delegation> delegations;
}
