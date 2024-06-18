package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Epreuve modifierEpreuve(long epreuveId, String nom, LocalDate date, int nbDelegations, int nbBillets, float prix, String infrastructureSportive) {
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        epreuve.setNom(nom);
        epreuve.setDate(date);
        epreuve.setNb_billets(nbBillets);
        epreuve.setNb_delegations(nbDelegations);
        epreuve.setPrix(prix);

        InfrastructureSportive infra = infrastructureSportiveRepository.findByNom(infrastructureSportive)
                .orElseThrow(() -> new RuntimeException("Infrastructure sportive non trouvée avec le nom : " + infrastructureSportive));

        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }



    public boolean verifierEmailExist(String email) {
        return organisateurRepository.findByEmail(email).isPresent();
    }

    public Delegation creerDelegation(Delegation delegation) {
        return delegationRepository.save(delegation);
    }

    @Transactional
    public void supprimerDelegation(long id) {
        Delegation delegation = delegationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Délégation non trouvée avec l'id' : " + id));

        List<Participant> participants = participantRepository.findByDelegation(delegation);
        for (Participant participant : participants) {
            participant.setDelegation(null);
            participantRepository.save(participant);
        }
        delegationRepository.delete(delegation);
    }

    public Epreuve creerEpreuve(Epreuve epreuve, long idInfrastructure) {

        InfrastructureSportive infra = infrastructureSportiveRepository.findById(idInfrastructure)
                .orElseThrow(() -> new RuntimeException("Infrastructure sportive non trouvée avec l'id : " + idInfrastructure));

        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }

    @Transactional
    public void supprimerEpreuve(long id) {
        Epreuve epreuve = epreuveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + id));

        epreuveRepository.delete(epreuve);
    }

    public Participant creerParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public String supprimerParticipant(long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("participant non trouvée avec id : " + id));
        participantRepository.delete(participant);
        return "Ok";
    }

    public Controleur creerControleur(Controleur controleur) {
        return controleurRepository.save(controleur);
    }

    public String supprimerControleur(String email) {
        Controleur controleur = controleurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("controleur non trouvée avec email : " + email));
        controleurRepository.delete(controleur);
        return "Ok";
    }


    public Epreuve setDate(LocalDate date, long idEpreuve) {
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow();
        epreuve.setDate(date);
        epreuveRepository.save(epreuve);
        return epreuve;
    }

    public String setNbParticipant(long epreuveId, int nbParticipant) {
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        int tailleMax = epreuve.getInfrastructureSportive().getCapacite();
        if(nbParticipant > tailleMax) {
            return "Impossible";
        }
        epreuve.setNb_delegations(nbParticipant);
        epreuveRepository.save(epreuve);
        return "Ok";
    }

    public Epreuve setNbBillets(long epreuveId, int nbBillets) throws Exception {
        Epreuve epreuve = epreuveRepository.findById(epreuveId).orElseThrow();
        if (epreuve.getInfrastructureSportive().getCapacite() < nbBillets){
            throw new Exception("Capacité insufisante");
        } else {
            epreuve.setNb_billets(nbBillets);
        }
        epreuveRepository.save(epreuve);
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

    public void setDelegation(String emailParticipant, long idDelegation) {
        Participant participant = participantRepository.findByEmail(emailParticipant)
                .orElseThrow(() -> new RuntimeException("Participant non trouvée avec le nom : " + emailParticipant));
        Delegation delegation = delegationRepository.findById(idDelegation)
                .orElseThrow(() -> new RuntimeException("Délégation non trouvée avec l'id : " + idDelegation));
        participant.setDelegation(delegation);
        participantRepository.save(participant);
    }

    public String setResultat(Resultat resultat, long epreuveId, String emailParticipant) {
        Epreuve epreuve = epreuveRepository.findById(epreuveId)
                .orElseThrow(() -> new RuntimeException("Epreuve non trouvée avec l'id : " + epreuveId));
        Participant participant = participantRepository.findByEmail(emailParticipant)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé avec l'email : " + emailParticipant));
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

        return "Ok";
    }

}
