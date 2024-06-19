package com.example.demo.dao;

import com.example.demo.entities.Epreuve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpreuveRepository extends CrudRepository<Epreuve, Long> {
    @Query("SELECT SUM(e.nb_billets) FROM Epreuve e")
    int getTotalPlacesDisponibles();

    @Query("SELECT SUM(b.prix) FROM Billet b WHERE b.etat = 'Payé'")
    Double getChiffreAffaires();

    @Query("SELECT COUNT(*) FROM Billet b WHERE b.etat = 'Payé'")
    int getTotalVentes();

}
