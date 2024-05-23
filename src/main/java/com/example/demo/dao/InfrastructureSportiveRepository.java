package com.example.demo.dao;

import com.example.demo.entities.InfrastructureSportive;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InfrastructureSportiveRepository extends CrudRepository<InfrastructureSportive, Long> {
    Optional<InfrastructureSportive> findByNom(String nom);
}
