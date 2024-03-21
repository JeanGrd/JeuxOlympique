package com.example.demo.dao;

import com.example.demo.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    public Client findByNomAndPrenom(String nom, String Prenom);

}
