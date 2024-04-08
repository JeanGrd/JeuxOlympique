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
    private long id;
    private int nbMedailleOr;
    private int nbMedailleArgent;
    private int nbMedailleBronze;

    @OneToMany(mappedBy = "delegation")
    private Collection<Participant> participants;
}