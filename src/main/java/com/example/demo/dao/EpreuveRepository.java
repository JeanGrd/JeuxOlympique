package com.example.demo.dao;

import com.example.demo.entities.Epreuve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Epreuve.
 */
@Repository
public interface EpreuveRepository extends CrudRepository<Epreuve, Long> {

    /**
     * Calcule le nombre total de places disponibles pour toutes les épreuves.
     *
     * @return le nombre total de places disponibles
     */
    @Query("SELECT SUM(e.nb_billets) FROM Epreuve e")
    int getTotalPlacesDisponibles();

    /**
     * Calcule le chiffre d'affaires total des billets payés.
     *
     * @return le chiffre d'affaires total
     */
    @Query("SELECT SUM(b.prix) FROM Billet b WHERE b.etat = 'Payé'")
    Double getChiffreAffaires();

    /**
     * Compte le nombre total de billets payés.
     *
     * @return le nombre total de ventes
     */
    @Query("SELECT COUNT(*) FROM Billet b WHERE b.etat = 'Payé'")
    int getTotalVentes();

}
