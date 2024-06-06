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
public class Spectateur {
    @Id
    @GeneratedValue
    private long spectateurId;
    private String nom;
    private String prenom;
    private String email;
}
