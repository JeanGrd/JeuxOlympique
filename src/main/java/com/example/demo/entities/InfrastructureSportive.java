// InfrastructureSportive.java
package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class InfrastructureSportive {

    @Id
    @GeneratedValue
    private long infrastructureId;
    private String nom;
    private String adresse;
    private int capacite;
    @OneToMany(mappedBy = "infrastructureSportive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Epreuve> epreuves;
}