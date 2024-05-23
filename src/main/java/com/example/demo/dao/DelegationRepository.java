package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DelegationRepository extends CrudRepository<Delegation, Long> {

    Optional<Delegation> findByNom(String nom);
}
