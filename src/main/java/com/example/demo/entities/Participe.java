package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant la participation d'une délégation à une épreuve.
 */
@Getter
@Setter
@Entity
public class Participe {

    /**
     * L'identifiant unique de la participation.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * L'état de la participation (e.g., "Participe", "Forfait").
     */
    private String etat;

    /**
     * L'épreuve à laquelle la délégation participe.
     */
    @ManyToOne
    @JoinColumn(name = "idEpreuve")
    private Epreuve epreuve;

    /**
     * La délégation qui participe à l'épreuve.
     */
    @ManyToOne
    @JoinColumn(name = "idDelegation")
    private Delegation delegation;
}
