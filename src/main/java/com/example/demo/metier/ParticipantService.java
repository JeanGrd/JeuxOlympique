package com.example.demo.metier;

import com.example.demo.dao.DelegationRepository;
import com.example.demo.dao.EpreuveRepository;
import com.example.demo.dao.ParticipantRepository;
import com.example.demo.dao.ResultatRepository;
import com.example.demo.entities.Delegation;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Resultat;
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
    @Autowired
    private ResultatRepository resultatRepository;
    @Autowired
    private DelegationRepository delegationRepository;

    public boolean verifierEmailExist(String email) {
        return participantRepository.findByEmail(email).isPresent();
    }

    public Participant inscrireEpreuve(long participantId, long epreuveId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow();
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        if (epreuve.getNb_delegations() >= epreuve.getDelegations().size())
            epreuve.getDelegations().add(participant.getDelegation());
        return participant;
    }

    public List<Epreuve> listerEpreuvesDisponibles() {
        // Utilisation de Java Streams pour convertir Iterable en List
        return StreamSupport.stream(epreuveRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Méthode pour consulter les résultats d'un participant spécifique
    public List<Resultat> consulterResultatsParticipant(long participantId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow();
        return resultatRepository.findByParticipant(participant);
    }

    // Méthode pour consulter les résultats de la délégation d'un participant
    public List<Resultat> consulterResultatsParDelegation(long participantId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow();
        long delegationId = participant.getDelegation().getDelegation_id();
        Delegation delegation = delegationRepository.findById(delegationId).orElseThrow();
        return resultatRepository.findByParticipant_Delegation(delegation);
    }
}