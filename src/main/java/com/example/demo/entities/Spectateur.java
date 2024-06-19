// Spectateur.java
package com.example.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Spectateur {

    @Id
    private String email;
    private long spectateurId;
    private String nom;
    private String prenom;
    @OneToMany(mappedBy = "spectateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Billet> billets;
}
