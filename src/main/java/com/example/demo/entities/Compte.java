package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Compte {

    @Id
    @GeneratedValue
    private long id_compte;
    private double solde;
    private boolean actif;
    @OneToMany
    private Collection<Operation> operation;
}
