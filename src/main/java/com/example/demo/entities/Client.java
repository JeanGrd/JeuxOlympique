package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

    private long id_client;
    private String nom;
    private String prenom;
}
