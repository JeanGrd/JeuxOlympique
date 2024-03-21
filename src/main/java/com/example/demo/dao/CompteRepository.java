package com.example.demo.dao;

import com.example.demo.entities.Compte;
import org.springframework.data.repository.CrudRepository;

public interface CompteRepository extends CrudRepository<Compte, Long> {

}
