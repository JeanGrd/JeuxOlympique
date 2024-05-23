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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long epreuve_id;

    private String nom;
    private Date date;
    private int nb_delegations;
    private int nb_billets;

    @ManyToOne
    @JoinColumn(name = "infrastructure_id")
    private InfrastructureSportive infrastructureSportive;

    @ManyToMany
    @JoinTable(
            name = "epreuve_delegation",
            joinColumns = @JoinColumn(name = "epreuve_id"),
            inverseJoinColumns = @JoinColumn(name = "delegation_id")
    )
    private Set<Delegation> delegations;
}
