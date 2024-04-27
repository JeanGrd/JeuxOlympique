// Délégation.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
public class Delegation {

    @Id
    @GeneratedValue
    private long delegation_id;
    private String nom;
    private int nb_medaille_or;
    private int nb_medaille_argent;
    private int nb_medaille_bronze;

}