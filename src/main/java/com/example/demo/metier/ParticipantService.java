package com.example.demo.service;

import com.example.demo.dao.ParticipantRepository;
import com.example.demo.dao.EpreuveRepository;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Epreuve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EpreuveRepository epreuveRepository;

    public Participant inscrireEpreuve(long participantId, long epreuveId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow();
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        // Ajouter des vérifications et logique métier
        return participantRepository.save(participant);
    }

    public List<Epreuve> listerEpreuvesDisponibles() {
        // Utilisation de Java Streams pour convertir Iterable en List
        return StreamSupport.stream(epreuveRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
