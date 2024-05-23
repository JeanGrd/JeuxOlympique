package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public Epreuve creerEpreuve(String nom, Date date, int nbDelegations, long infrastructureSportiveId) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNom(nom);
        epreuve.setDate(date);
        epreuve.setNb_delegations(nbDelegations);

        InfrastructureSportive infra = infrastructureSportiveRepository.findById(infrastructureSportiveId)
                .orElseThrow(() -> new RuntimeException("Infrastructure sportive non trouvée avec l'ID : " + infrastructureSportiveId));

        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }

    public boolean verifierEmailExist(String email) {
        return organisateurRepository.findByEmail(email).isPresent();
    }

    public Delegation creerDelegation(Delegation delegation) {
        return delegationRepository.save(delegation);
    }

    public void supprimerDelegation(long delegationId) {
        delegationRepository.deleteById(delegationId);
    }

    public void supprimerEpreuve(long epreuveId) {
        epreuveRepository.deleteById(epreuveId);
    }

    public Participant creerParticipant(String nom, String prenom, String email, Delegation delegation) {
        Participant participant = new Participant();
        participant.setNom(nom);
        participant.setPrenom(prenom);
        participant.setEmail(email);
        participant.setDelegation(delegation);
        return participantRepository.save(participant);
    }

    public Controleur creerControleur(String nom, String prenom, String email) {
        Controleur controleur = new Controleur();
        controleur.setNom(nom);
        controleur.setPrenom(prenom);
        controleur.setEmail(email);
        return controleurRepository.save(controleur);
    }

    public Epreuve setDate(Epreuve epreuve, Date date) {
        epreuve.setDate(date);
        return epreuve;
    }

    public Epreuve setNbParticipant(Epreuve epreuve, int nbParticipant) {
        epreuve.setNb_delegations(nbParticipant);
        return epreuve;
    }

    public Epreuve setNbBillets(Epreuve epreuve, int nbBillets) {
        epreuve.setNb_billets(nbBillets);
        return epreuve;
    }


    public int getTotalPlacesDisponibles() {
        return epreuveRepository.getTotalPlacesDisponibles();
    }

    public double getChiffreAffaires() {
        return epreuveRepository.getChiffreAffaires();
    }

    // ???
    public Participant setDelegation(Participant participant, Delegation delegation) {
        participant.setDelegation(delegation);
        return participantRepository.save(participant);
    }
}
