// Délégation.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
public class Delegation extends Entite {

    private int nbMedailleOr;
    private int nbMedailleArgent;
    private int nbMedailleBronze;

    @OneToMany(mappedBy = "delegation")
    private Collection<Participant> participants;
}