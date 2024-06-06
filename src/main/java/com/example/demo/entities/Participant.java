// Participant.java
package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Participant {

    @Id
    @GeneratedValue
    private long participantId;
    private String nom;
    private String prenom;
    private String email;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
}