package com.example.demo.dto;

import com.example.demo.entities.Epreuve;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EpreuveDTO {
    private Long idEpreuve; // Utiliser Long au lieu de long pour permettre des valeurs nulles
    private String nom;
    private LocalDate date;
    private Integer nbDelegations; // Utiliser Integer au lieu de int
    private Integer nbBillets; // Utiliser Integer au lieu de int
    private Double prix; // Utiliser Double au lieu de double
    private Long idInfrastructure;

}
