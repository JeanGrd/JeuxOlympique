package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Billet {
    @Id
    @GeneratedValue
    private long id_billet;

    @JoinColumn(name = "spectateur_id", referencedColumnName = "id")
    private Spectateur spectateur;
    @JoinColumn(name = "epreuve_id", referencedColumnName = "id")
    private Participant participant;
}
