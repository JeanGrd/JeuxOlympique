package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Delegation {

    @Id
    @GeneratedValue
    private long delegationId;
    private String nom;
    private int nb_medaille_or;
    private int nb_medaille_argent;
    private int nb_medaille_bronze;
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participe> participes;
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
}
