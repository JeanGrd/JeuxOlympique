package com.example.demo.dao;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BilletRepository extends CrudRepository<Billet, Long> {
    int countAllBySpectateurAndEpreuve(Spectateur spectateur, Epreuve epreuve);

    Optional<Billet> findByBilletIdAndSpectateur_Email(long idBillet, String email);
}
