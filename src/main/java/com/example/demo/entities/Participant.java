// Participant.java
package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultat> resultats;
}