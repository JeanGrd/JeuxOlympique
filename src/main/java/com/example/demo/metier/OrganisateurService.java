package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Transactional
    public void supprimerDelegation(String delegationNom) {
        Delegation delegation = delegationRepository.findByNom(delegationNom)
                .orElseThrow(() -> new RuntimeException("Délégation non trouvée avec le nom : " + delegationNom));

        List<Participant> participants = participantRepository.findByDelegation(delegation);
        for (Participant participant : participants) {
            participant.setDelegation(null); // Ou bien participantRepository.delete(participant); si vous voulez les supprimer
            participantRepository.save(participant);
        }
        delegationRepository.delete(delegation);
    }


    @Transactional
    public void supprimerEpreuve(String epreuveNom) {
        Epreuve epreuve = epreuveRepository.findByNom(epreuveNom)
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec le nom : " + epreuveNom));

        // Supprimez toutes les relations avec les délégations ici si nécessaire

        epreuveRepository.delete(epreuve);
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

    public String supprimerParticipant(String email) {
        Participant participant = participantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("participant non trouvée avec email : " + email));
        participantRepository.delete(participant);
        return "Ok";
    }


    public String supprimerControleur(String email) {
        Controleur controleur = controleurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("controleur non trouvée avec email : " + email));
        controleurRepository.delete(controleur);
        return "Ok";
    }


    public Epreuve setDate(Epreuve epreuve, Date date) {
        epreuve.setDate(date);
        return epreuve;
    }

    public String setNbParticipant(Epreuve epreuve, int nbParticipant) {
        int tailleMax = epreuve.getInfrastructureSportive().getCapacite();
        if(nbParticipant > tailleMax) {
            return "Impossible";
        }
        epreuve.setNb_delegations(nbParticipant);
        return "Ok";
    }

    public Epreuve setNbBillets(Epreuve epreuve, int nbBillets) {
        epreuve.setNb_billets(nbBillets);
        return epreuve;
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

    public void setDelegation(String participantEmail, String delegationNom) {
        Participant participant = participantRepository.findByEmail(participantEmail)
                .orElseThrow(() -> new RuntimeException("Participant non trouvée avec le nom : " + participantEmail));
        Delegation delegation = delegationRepository.findByNom(delegationNom)
                .orElseThrow(() -> new RuntimeException("Délégation non trouvée avec le nom : " + delegationNom));
        participant.setDelegation(delegation);
        participantRepository.save(participant);
    }

    public String setResultat(double point, int position, long epreuveId, long participantId) {
        Resultat resultat = new Resultat();
        Epreuve epreuve = epreuveRepository.findById(epreuveId)
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + epreuveId));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Particpant non trouvée avec l'id : " + participantId));
        resultat.setPoint(point);
        resultat.setPosition(position);
        resultat.setEpreuve(epreuve);
        resultat.setParticipant(participant);
        resultatRepository.save(resultat);
        return "Ok";
    }

    public String updateResultatGlobal(long epreuveId) {
        Epreuve epreuve = epreuveRepository.findById(epreuveId)
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + epreuveId));

        List<Resultat> resultats = resultatRepository.findByEpreuveOrderByPositionAsc(epreuve);

        if (resultats.size() < 3) {
            return "Pas assez de résultats pour déterminer les trois premiers.";
        }

        Delegation orDelegation = resultats.get(0).getParticipant().getDelegation();
        Delegation argentDelegation = resultats.get(1).getParticipant().getDelegation();
        Delegation bronzeDelegation = resultats.get(2).getParticipant().getDelegation();

        orDelegation.setNb_medaille_or(orDelegation.getNb_medaille_or() + 1);
        argentDelegation.setNb_medaille_argent(argentDelegation.getNb_medaille_argent() + 1);
        bronzeDelegation.setNb_medaille_bronze(bronzeDelegation.getNb_medaille_bronze() + 1);

        delegationRepository.save(orDelegation);
        delegationRepository.save(argentDelegation);
        delegationRepository.save(bronzeDelegation);

        return "Classement mis à jour avec succès";
    }
}
