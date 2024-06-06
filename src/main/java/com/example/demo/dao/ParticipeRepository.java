package com.example.demo.dao;

import com.example.demo.entities.Participe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParticipeRepository extends CrudRepository<Participe, Long> {

    Optional<Participe> findByDelegation_DelegationIdAndEpreuve_EpreuveId(long delegationId, long epreuveId);
}
