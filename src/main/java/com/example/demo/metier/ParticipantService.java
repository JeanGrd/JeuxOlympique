package com.example.demo.metier;

import com.example.demo.dao.EpreuveRepository;
import com.example.demo.dao.ParticipantRepository;
import com.example.demo.dao.ResultatRepository;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Resultat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EpreuveRepository epreuveRepository;
    @Autowired
    private ResultatRepository resultatRepository;


    public boolean verifierEmailExist(String email) {
        return participantRepository.findByEmail(email).isPresent();
    }

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

    public List<Resultat> consulterResultats() {
        // Utilisation de Java Streams pour convertir Iterable en List
        return StreamSupport.stream(resultatRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
