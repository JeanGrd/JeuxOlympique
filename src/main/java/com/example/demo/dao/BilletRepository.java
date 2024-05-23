package com.example.demo.dao;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BilletRepository extends CrudRepository<Billet, Long> {
    int countAllBySpectateurAndEpreuve(Spectateur spectateur, Epreuve epreuve);

}
