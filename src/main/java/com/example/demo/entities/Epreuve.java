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
public class Epreuve extends Entite {

    private Date date;
    private int nbPlaces;

    @ManyToOne
    @JoinColumn(name = "infrastructureSportiveId")
    private InfrastructureSportive infrastructureSportive;

}
