// Spectateur.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Controleur {
    @Id
    @GeneratedValue
    private long controleurId;
    private String nom;
    private String prenom;
    private String email;
}
