// Participant.java
package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant un participant aux épreuves.
 */
@Getter
@Setter
@Entity
public class Participant {

    /**
     * L'email du participant (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * Le nom du participant.
     */
    private String nom;

    /**
     * Le prénom du participant.
     */
    private String prenom;

    /**
     * La délégation à laquelle appartient le participant.
     */
    @ManyToOne
    @JoinColumn(name = "idDelegation")
    @JsonIgnore
    private Delegation delegation;

    /**
     * La liste des résultats obtenus par le participant.
     * La relation est en cascade et les résultats orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Resultat> resultats;
}