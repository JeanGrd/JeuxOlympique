package com.example.demo.dao;

import com.example.demo.entities.Participe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipeRepository extends CrudRepository<Participe, Long> {

    Optional<Participe> findByDelegation_IdAndEpreuve_Id(long delegationId, long epreuveId);

}
