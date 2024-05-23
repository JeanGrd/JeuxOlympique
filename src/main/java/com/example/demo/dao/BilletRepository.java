package com.example.demo.dao;

import com.example.demo.entities.Billet;
import org.springframework.data.repository.CrudRepository;

public interface BilletRepository extends CrudRepository<Billet, Long> {
    int countBySpectateurIdAndEpreuveId(long spectateurId, long epreuveId);
}
