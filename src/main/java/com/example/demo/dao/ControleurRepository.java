package com.example.demo.dao;

import com.example.demo.entities.Controleur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ControleurRepository extends CrudRepository<Controleur, Long> {
    Optional<Controleur> findByEmail(String email);
}
