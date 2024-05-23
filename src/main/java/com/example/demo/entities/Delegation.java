// Délégation.java
package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

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