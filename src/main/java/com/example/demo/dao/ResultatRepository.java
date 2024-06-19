package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Resultat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatRepository extends CrudRepository<Resultat, Long> {
    List<Resultat> findByParticipant_Delegation(Delegation participant_delegation);

    List<Resultat> findByParticipant(Participant participant);

}
