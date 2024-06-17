package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private ParticipeRepository participeRepository;
    @Autowired
    private ResultatRepository resultatRepository;
    @Autowired
    private DelegationRepository delegationRepository;

    public boolean verifierEmailExist(String email) {
        return participantRepository.findByEmail(email).isPresent();
    }

    public String inscrireEpreuve(String email, long epreuveId) {
        Participant participant = participantRepository.findByEmail(email).orElseThrow();
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();

        Delegation delegation = participant.getDelegation();

        if (delegation == null) {
            return ("Le participant n'est associé à aucune délégation.");
        }

        boolean alreadyExists = participeRepository.findByDelegation_DelegationIdAndEpreuve_EpreuveId(delegation.getDelegationId(), epreuveId).isPresent();
        // Vérifier si l'inscription est possible (avant 10 jours de la date de l'épreuve)
        LocalDate now = LocalDate.now();
        LocalDate dateEpreuve = epreuve.getDate();
        if (ChronoUnit.DAYS.between(now, dateEpreuve) > 10) {
            if (epreuve.getNb_delegations() >= epreuve.getNb_delegations()) {
                if (!alreadyExists) {
                    Participe participes = new Participe();
                    participes.setEpreuve(epreuve);
                    participes.setDelegation(delegation);
                    participes.setEtat("Participe");

                    participes.setDelegation(delegation);
                    participes.setEpreuve(epreuve);

                    participeRepository.save(participes);

                } else {
                    return "La délégation est déjà inscrite à cette épreuve.";
                }
            }
        } else {
            return "inscription est fermée 10 jours avant la date de l'épreuve.";
        }
        return "La délégation est bien inscrite";
    }

    public String desengagerEpreuve(String email, long epreuveId) {
        // Récupérer l'épreuve
        Epreuve epreuve = epreuveRepository.findById(epreuveId)
                .orElseThrow(() -> new EntityNotFoundException("Epreuve not found"));
        Participant participant = participantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found"));
        Delegation delegation = delegationRepository.findById(participant.getDelegation().getDelegationId())
                .orElseThrow(() -> new EntityNotFoundException("Epreuve not found"));

        // Vérifier si la date de l'épreuve est dans les 10 jours
        LocalDate now = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(now, epreuve.getDate());

        // Récupérer la participation
        Participe participation = participeRepository.findByDelegation_DelegationIdAndEpreuve_EpreuveId(delegation.getDelegationId(), epreuveId)
                .orElseThrow(() -> new EntityNotFoundException("Participation not found"));

        if (daysBetween <= 10) {
            // Si dans les 10 jours, marquer comme forfait
            participation.setEtat("forfait");
            participeRepository.save(participation);
            return "Participant désengagé et marqué comme forfait";
        } else {
            System.out.println(participation.getDelegation().getDelegationId());
            System.out.println(participation.getEpreuve().getEpreuveId());
            System.out.println(participation.getId());

            // Sinon, supprimer la participation
            participeRepository.delete(participation);
            return "Participant désengagé avec succès";
        }
    }

    // Méthode pour consulter les résultats d'un participant spécifique
    public List<Resultat> consulterResultatsParticipant(String email) {
        Participant participant = participantRepository.findByEmail(email).orElseThrow();
        return resultatRepository.findByParticipant(participant);
    }

    // Méthode pour consulter les résultats de la délégation d'un participant
    public List<Resultat> consulterResultatsParDelegation(String email) {
        Participant participant = participantRepository.findByEmail(email).orElseThrow();
        long delegationId = participant.getDelegation().getDelegationId();
        Delegation delegation = delegationRepository.findById(delegationId).orElseThrow();
        return resultatRepository.findByParticipant_Delegation(delegation);
    }

    public List<Epreuve> consulterProgramme() {
        Iterable<Epreuve> epreuves = epreuveRepository.findAll();
        return StreamSupport.stream(epreuves.spliterator(), false)
                .collect(Collectors.toList());
    }
}
