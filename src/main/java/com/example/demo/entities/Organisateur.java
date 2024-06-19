// Organisateur.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un organisateur.
 */
@Getter
@Setter
@Entity
public class Organisateur {

    /**
     * L'email de l'organisateur (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * Le nom de l'organisateur.
     */
    private String nom;

    /**
     * Le prénom de l'organisateur.
     */
    private String prenom;

    /**
     * Le rôle de l'organisateur.
     */
    private String role;
}