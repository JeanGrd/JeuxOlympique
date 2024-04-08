// Participant.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Participant extends Personne {

    @ManyToOne
    @JoinColumn(name = "delegation_id")
    private Delegation delegation;
}