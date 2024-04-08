// InfrastructureSportive.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InfrastructureSportive {

    @Id
    @GeneratedValue
    private long id;
    private String adresse;
    private int capacite;
}