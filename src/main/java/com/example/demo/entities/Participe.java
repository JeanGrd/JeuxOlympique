package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Participe {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "epreuveId")
    private Epreuve epreuve;
    @ManyToOne
    @JoinColumn(name = "delegationId")
    private Delegation delegation;
    private String etat;
}
