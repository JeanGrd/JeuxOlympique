package com.example.demo.metier;

import com.example.demo.dao.*;
import com.example.demo.entities.InfrastructureSportive;
import com.example.demo.entities.Organisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
