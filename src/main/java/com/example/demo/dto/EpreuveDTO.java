package com.example.demo.dto;

import com.example.demo.entities.Epreuve;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) pour les épreuves.
 * Ce DTO est utilisé pour transférer les données des épreuves entre les couches de l'application.
 */
@Getter
@Setter
public class EpreuveDTO {

    /**
     * L'identifiant de l'épreuve.
     */
    private Long idEpreuve;

    /**
     * Le nom de l'épreuve.
     */
    private String nom;

    /**
     * La date de l'épreuve.
     */
    private LocalDate date;

    /**
     * Le nombre maximum de délégations participant à l'épreuve.
     */
    private Integer nbDelegations;

    /**
     * Le nombre de billets disponibles pour l'épreuve.
     */
    private Integer nbBillets;

    /**
     * Le prix du billet pour l'épreuve.
     */
    private Double prix;

    /**
     * L'identifiant de l'infrastructure sportive où se déroule l'épreuve.
     */
    private Long idInfrastructure;

}
