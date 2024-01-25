package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Compte {

    private long id_compte;
    private double solde;
    private boolean actif;
}
