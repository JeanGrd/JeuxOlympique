package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.dto.EpreuveDTO;
import com.example.demo.dto.ResultatDTO;
import com.example.demo.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrganisateurService {

    @Autowired
    private EpreuveRepository epreuveRepository;
    @Autowired
    private DelegationRepository delegationRepository;
    @Autowired
    private InfrastructureSportiveRepository infrastructureSportiveRepository;
    @Autowired
    private OrganisateurRepository organisateurRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ControleurRepository controleurRepository;
    @Autowired
    private ResultatRepository resultatRepository;

    public boolean verifierEmailExist(String email) {
        return organisateurRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void creerDelegation(Delegation delegation) {
        delegationRepository.save(delegation);
    }

    @Transactional
    public void supprimerDelegation(long id) {
        Delegation delegation = delegationRepository.findById(id).orElseThrow(() -> new RuntimeException("Délégation non trouvée avec l'id' : " + id));

        List<Participant> participants = participantRepository.findByDelegation(delegation);
        for (Participant participant : participants) {
            participant.setDelegation(null);
            participantRepository.save(participant);
        }
        delegationRepository.delete(delegation);
    }

    @Transactional
    public Epreuve creerEpreuve(EpreuveDTO epreuveDTO) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNom(epreuveDTO.getNom());
        epreuve.setDate(epreuveDTO.getDate());
        epreuve.setNb_delegations(epreuveDTO.getNbDelegations());
        epreuve.setNb_billets(epreuveDTO.getNbBillets());
        epreuve.setPrix(epreuveDTO.getPrix());
        InfrastructureSportive infra = infrastructureSportiveRepository.findById(epreuveDTO.getIdInfrastructure()).orElseThrow(()
                -> new RuntimeException("Infrastructure sportive non trouvée avec l'id : " + epreuveDTO.getIdInfrastructure()));
        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }

    @Transactional
    public void supprimerEpreuve(long id) {
        Epreuve epreuve = epreuveRepository.findById(id).orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + id));

        epreuveRepository.delete(epreuve);
    }

    @Transactional
    public void modifierEpreuve(EpreuveDTO epreuveDTO) {
        Epreuve epreuve = epreuveRepository.findById(epreuveDTO.getIdEpreuve())
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + epreuveDTO.getIdEpreuve()));

        if (epreuveDTO.getIdInfrastructure() != null) {
            InfrastructureSportive infra = infrastructureSportiveRepository.findById(epreuveDTO.getIdInfrastructure())
                    .orElseThrow(() -> new RuntimeException("Infrastructure sportive non trouvée avec l'id : " + epreuveDTO.getIdInfrastructure()));
            epreuve.setInfrastructureSportive(infra);
        }

        if (epreuveDTO.getNom() != null) {
            epreuve.setNom(epreuveDTO.getNom());
        }
        if (epreuveDTO.getDate() != null) {
            epreuve.setDate(epreuveDTO.getDate());
        }
        if (epreuveDTO.getNbDelegations() != null) {
            epreuve.setNb_delegations(epreuveDTO.getNbDelegations());
        }
        if (epreuveDTO.getNbBillets() != null) {
            epreuve.setNb_billets(epreuveDTO.getNbBillets());
        }
        if (epreuveDTO.getPrix() != null) {
            epreuve.setPrix(epreuveDTO.getPrix());
        }

        epreuveRepository.save(epreuve);
    }

    @Transactional
    public Participant creerParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Transactional
    public void supprimerParticipant(long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new RuntimeException("participant non trouvée avec id : " + id));
        participantRepository.delete(participant);
    }

    @Transactional
    public Controleur creerControleur(Controleur controleur) {
        return controleurRepository.save(controleur);
    }

    @Transactional
    public void supprimerControleur(String email) {
        Controleur controleur = controleurRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("controleur non trouvée avec email : " + email));
        controleurRepository.delete(controleur);
    }

    @Transactional
    public void setDate(LocalDate date, long idEpreuve) {
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow();
        epreuve.setDate(date);
        epreuveRepository.save(epreuve);
    }

    @Transactional
    public String setNbParticipants(long epreuveId, int nbParticipant) {
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        int tailleMax = epreuve.getInfrastructureSportive().getCapacite();
        if (nbParticipant > tailleMax) {
            return "Nombre de participants supérieur à la taille maximum de l'infrastructure.";
        }
        epreuve.setNb_delegations(nbParticipant);
        epreuveRepository.save(epreuve);
        return "Nombre de participant mis-à-jour.";
    }

    @Transactional
    public String setNbBillets(long epreuveId, int nbBillets) {
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        if (epreuve.getInfrastructureSportive().getCapacite() < nbBillets) {
            return "Nombre de billets supérieur à la taille maximum de l'infrastructure.";
        } else {
            epreuve.setNb_billets(nbBillets);
        }
        epreuveRepository.save(epreuve);
        return "Nombre de billets mis-à-jour.";
    }

    public int getTotalPlacesDisponibles() {
        return epreuveRepository.getTotalPlacesDisponibles();
    }

    public double getChiffreAffaires() {
        Double chiffreAffaires = epreuveRepository.getChiffreAffaires();
        return chiffreAffaires != null ? chiffreAffaires : 0.0;
    }

    public int getTotalVentes() {
        return epreuveRepository.getTotalVentes();
    }

    @Transactional
    public void setDelegation(String emailParticipant, long idDelegation) {
        Participant participant = participantRepository.findByEmail(emailParticipant).orElseThrow(() -> new RuntimeException("Participant non trouvée avec le nom : " + emailParticipant));
        Delegation delegation = delegationRepository.findById(idDelegation).orElseThrow(() -> new RuntimeException("Délégation non trouvée avec l'id : " + idDelegation));
        participant.setDelegation(delegation);
        participantRepository.save(participant);
    }

    @Transactional
    public void setResultat(ResultatDTO resultatDTO) {
        Epreuve epreuve = epreuveRepository.findById(resultatDTO.getIdEpreuve()).orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + resultatDTO.getIdEpreuve()));
        Participant participant = participantRepository.findByEmail(resultatDTO.getEmailParticipant()).orElseThrow(() -> new RuntimeException("Participant non trouvé avec l'email : " + resultatDTO.getEmailParticipant()));
        Resultat resultat = new Resultat();
        resultat.setPoint(resultatDTO.getPoint());
        resultat.setPosition(resultatDTO.getPosition());
        resultat.setEpreuve(epreuve);
        resultat.setParticipant(participant);

        resultatRepository.save(resultat);

        if (resultat.getPosition() >= 0 && resultat.getPosition() <= 2) {
            Delegation delegation = resultat.getParticipant().getDelegation();
            if (resultat.getPosition() == 0) {
                delegation.setNb_medaille_or(delegation.getNb_medaille_or() + 1);
            } else if (resultat.getPosition() == 1) {
                delegation.setNb_medaille_argent(delegation.getNb_medaille_argent() + 1);
            } else {
                delegation.setNb_medaille_bronze(delegation.getNb_medaille_bronze() + 1);
            }
            delegationRepository.save(delegation);
        }

    }

}
