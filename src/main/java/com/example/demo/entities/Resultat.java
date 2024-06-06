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
    private long resultatId;
    private double point;
    private int position;
    @ManyToOne
    @JoinColumn(name = "participantId")
    private Participant participant;
    @ManyToOne
    @JoinColumn(name = "epreuveId")
    private Epreuve epreuve;
}