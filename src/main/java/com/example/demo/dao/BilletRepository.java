package com.example.demo.dao;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Billet.
 */
@Repository
public interface BilletRepository extends CrudRepository<Billet, Long> {

    /**
     * Compte le nombre de billets pour un spectateur et une épreuve donnés.
     *
     * @param spectateur le spectateur
     * @param epreuve    l'épreuve
     * @return le nombre de billets
     */
    int countAllBySpectateurAndEpreuve(Spectateur spectateur, Epreuve epreuve);

    /**
     * Recherche un billet par son identifiant et l'email du spectateur.
     *
     * @param idBillet l'identifiant du billet
     * @param email    l'email du spectateur
     * @return un Optional contenant le billet s'il est trouvé, ou vide sinon
     */
    Optional<Billet> findByIdAndSpectateur_Email(long idBillet, String email);

    /**
     * Recherche tous les billets pour un spectateur donné par son email.
     *
     * @param email l'email du spectateur
     * @return une liste de billets pour le spectateur spécifié
     */
    List<Billet> findAllBySpectateur_Email(String email);
}
