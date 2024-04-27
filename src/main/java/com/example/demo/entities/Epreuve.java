package com.example.demo.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Epreuve {

    @Id
    @GeneratedValue
    private long epreuve_id;
    private String nom;
    private Date date;
    private int nb_places;
    @ManyToOne
    @JoinColumn(name = "infrastructure_id")
    private InfrastructureSportive infrastructureSportive;

}
