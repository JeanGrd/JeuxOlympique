// Résultat.java
package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Resultat {

    @Id
    @GeneratedValue
    private long resultat_id;
    private double point;
    private int position;
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;
    @ManyToOne
    @JoinColumn(name = "epreuve_id")
    private Epreuve epreuve;
}