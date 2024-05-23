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

    public Epreuve creerEpreuve(String nom, Date date, int nbDelegations, String infrastructureSportive) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNom(nom);
        epreuve.setDate(date);
        epreuve.setNb_delegations(nbDelegations);

        InfrastructureSportive infra = infrastructureSportiveRepository.findByNom(infrastructureSportive)
                .orElseThrow(() -> new RuntimeException("Infrastructure sportive non trouvée avec le nom : " + infrastructureSportive));

        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }

    public boolean verifierEmailExist(String email) {
        return organisateurRepository.findByEmail(email).isPresent();
    }

    public Delegation creerDelegation(String nom) {
        Delegation delegation = new Delegation();
        delegation.setNom(nom);
        delegation.setNb_medaille_bronze(0);
        delegation.setNb_medaille_argent(0);
        delegation.setNb_medaille_or(0);
        return delegationRepository.save(delegation);
    }

    public void supprimerDelegation(long delegationId) {
        delegationRepository.deleteById(delegationId);
    }

    public void supprimerEpreuve(long epreuveId) {
        epreuveRepository.deleteById(epreuveId);
    }

    public Participant creerParticipant(String nom, String prenom, String email) {
        Participant participant = new Participant();
        participant.setNom(nom);
        participant.setPrenom(prenom);
        participant.setEmail(email);
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

    public double getTotalVentes() {
        return epreuveRepository.getTotalVentes();
    }

    // ???
    public String setDelegation(String participantEmail, String delegationNom) {
        Participant participant = participantRepository.findByEmail(participantEmail)
                .orElseThrow(() -> new RuntimeException("Participant non trouvée avec le nom : " + participantEmail));;
        Delegation delegation = delegationRepository.findByNom(delegationNom)
                .orElseThrow(() -> new RuntimeException("Délégation non trouvée avec le nom : " + delegationNom));;
        participant.setDelegation(delegation);
        participantRepository.save(participant);
        return "ok";
    }
}
