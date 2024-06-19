package com.example.demo.dto;

import com.example.demo.entities.Epreuve;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EpreuveDTO {
    private Long idEpreuve;
    private String nom;
    private LocalDate date;
    private Integer nbDelegations;
    private Integer nbBillets;
    private Double prix;
    private Long idInfrastructure;

}
