// Résultat.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Resultat {

    @Id
    @GeneratedValue
    private long id;
    private long idEpreuve;
    private long idParticipant;
    private double temps;
    private int position;

    @ManyToOne
    @JoinColumn(name = "participantId", referencedColumnName = "id")
    private Participant participant;
}