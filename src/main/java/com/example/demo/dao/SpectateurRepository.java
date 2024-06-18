package com.example.demo.dao;

import com.example.demo.entities.Spectateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpectateurRepository extends CrudRepository<Spectateur, Long> {
    Optional<Spectateur> findByEmail(String email);

    void deleteByEmail(String email);
}
