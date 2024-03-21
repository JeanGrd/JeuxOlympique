package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue
    private long id_client;
    private String nom;
    private String prenom;
    @OneToMany
    private Collection<Compte> compte;
}
