package com.example.demo.dao;

import com.example.demo.entities.Epreuve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EpreuveRepository extends CrudRepository<Epreuve, Long> {
    @Query("SELECT SUM(e.nb_delegations) FROM Epreuve e")
    int getTotalPlacesDisponibles();

    @Query("SELECT SUM(r.montant) FROM Reservation r")
    double getChiffreAffaires();
}
