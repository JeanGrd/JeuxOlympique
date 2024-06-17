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
    private String email;
    private String nom;
    private String prenom;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
}