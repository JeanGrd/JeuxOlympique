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

import java.time.LocalDate;
import java.time.ZoneId;
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
    private ResultatRepository resultatRepository;
    @Autowired
    private DelegationRepository delegationRepository;

    public boolean verifierEmailExist(String email) {
        return participantRepository.findByEmail(email).isPresent();
    }

    public Participant inscrireEpreuve(long participantId, long epreuveId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow();
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();

        // Vérifier si l'inscription est possible (avant 10 jours de la date de l'épreuve)
        LocalDate now = LocalDate.now();
        LocalDate dateEpreuve = epreuve.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (ChronoUnit.DAYS.between(now, dateEpreuve) > 10) {
            if (epreuve.getNb_delegations() >= epreuve.getDelegations().size()) {
                epreuve.getDelegations().add(participant.getDelegation());
                epreuve.setEtat("Participe");
                epreuveRepository.save(epreuve); // Sauvegarder les modifications
                return participant;
            }
        } else {
            throw new IllegalArgumentException("L'inscription est fermée 10 jours avant la date de l'épreuve.");
        }
        return participant;
    }

    public String desengagerEpreuve(long participantId, long epreuveId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé avec l'id : " + participantId));
        Epreuve epreuve = epreuveRepository.findByEpreuve_idAndDelegations(epreuveId, participant.getDelegation())
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + epreuveId));

        LocalDate now = LocalDate.now();
        LocalDate dateEpreuve = epreuve.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean isForfait = ChronoUnit.DAYS.between(now, dateEpreuve) <= 10;

        if (isForfait) {
            epreuve.setEtat("Forfait");
            epreuveRepository.save(epreuve);
            return "Participant marqué comme forfait pour l'épreuve.";
        } else {
            epreuveRepository.delete(epreuve);
            return "Désengagement réussi.";
        }
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
