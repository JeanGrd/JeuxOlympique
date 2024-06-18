// Participant.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Participant {

    @Id
    private String email;
    private String nom;
    private String prenom;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
}