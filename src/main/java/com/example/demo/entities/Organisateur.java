// Organisateur.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Organisateur extends Personne {

    private String role;
}