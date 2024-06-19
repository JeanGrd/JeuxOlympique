package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import com.example.demo.entities.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    Optional<Participant> findByEmail(String email);

    List<Participant> findByDelegation(Delegation delegation);

}
