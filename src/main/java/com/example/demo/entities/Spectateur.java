// Spectateur.java
package com.example.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant un spectateur.
 * Un spectateur peut réserver des billets pour des épreuves.
 */
@Getter
@Setter
@Entity
public class Spectateur {

    /**
     * L'email du spectateur (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * L'identifiant du spectateur.
     */
    private String nom;

    /**
     * Le prénom du spectateur.
     */
    private String prenom;

    /**
     * La liste des billets réservés par le spectateur.
     * La relation est en cascade et les billets orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "spectateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Billet> billets;
}
