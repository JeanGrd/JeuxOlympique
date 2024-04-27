package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
public class AdminService {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private DelegationRepository delegationRepository;

    @Autowired
    private EpreuveRepository epreuveRepository;

    @Autowired
    private InfrastructureSportiveRepository infrastructureSportiveRepository;

    @Autowired
    private OrganisateurRepository organisateurRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private SpectateurRepository spectateurRepository;

    public Billet creerBillet(double prix, Epreuve epreuve, Spectateur spectateur) {
        Billet billet = new Billet();
        billet.setPrix(prix);
        billet.setEpreuve(epreuve);
        billet.setSpectateur(spectateur);
        return billetRepository.save(billet);
    }

    public Delegation creerDelegation(String nom) {
        Delegation delegation = new Delegation();
        delegation.setNom(nom);
        return delegationRepository.save(delegation);
    }

    public Epreuve creerEpreuve(String nom, Date date, int nbPlaces, InfrastructureSportive infrastructure) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNom(nom);
        epreuve.setDate(date);
        epreuve.setNb_places(nbPlaces);
        epreuve.setInfrastructureSportive(infrastructure);
        return epreuveRepository.save(epreuve);
    }

    public InfrastructureSportive creerInfrastructureSportive(String nom, int capacite, String adresse) {
        InfrastructureSportive infrastructure = new InfrastructureSportive();
        infrastructure.setNom(nom);
        infrastructure.setCapacite(capacite);
        infrastructure.setAdresse(adresse);
        return infrastructureSportiveRepository.save(infrastructure);
    }

    public Organisateur creerOrganisateur(String nom, String prenom, String email, String role) {
        Organisateur organisateur = new Organisateur();
        organisateur.setNom(nom);
        organisateur.setPrenom(prenom);
        organisateur.setEmail(email);
        organisateur.setRole(role);
        return organisateurRepository.save(organisateur);
    }

    public Participant creerParticipant(String nom, String prenom, String email, Delegation delegation) {
        Participant participant = new Participant();
        participant.setNom(nom);
        participant.setPrenom(prenom);
        participant.setEmail(email);
        participant.setDelegation(delegation);
        return participantRepository.save(participant);
    }

    public Resultat creerResultat(int position, float temps, Epreuve epreuve, Participant participant) {
        Resultat resultat = new Resultat();
        resultat.setPosition(position);
        resultat.setTemps(temps);
        resultat.setEpreuve(epreuve);
        resultat.setParticipant(participant);
        return resultatRepository.save(resultat);
    }

    public Spectateur creerSpectateur(String nom, String prenom, String email) {
        Spectateur spectateur = new Spectateur();
        spectateur.setNom(nom);
        spectateur.setPrenom(prenom);
        spectateur.setEmail(email);
        return spectateurRepository.save(spectateur);
    }
}
