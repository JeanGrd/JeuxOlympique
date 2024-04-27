package com.example.demo.dao;

import com.example.demo.entities.Spectateur;
import org.springframework.data.repository.CrudRepository;

public interface SpectateurRepository extends CrudRepository<Spectateur, Long> {
}
