package com.example.demo.dao;

import com.example.demo.entities.Organisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisateurRepository extends CrudRepository<Organisateur, Long> {
    Optional<Organisateur> findByEmail(String email);

}
