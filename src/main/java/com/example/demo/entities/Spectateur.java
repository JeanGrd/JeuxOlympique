// Spectateur.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Spectateur {

    @Id
    private String email;
    private long spectateurId;
    private String nom;
    private String prenom;

}
