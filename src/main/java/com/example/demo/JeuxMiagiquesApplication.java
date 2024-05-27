package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.metier.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class JeuxMiagiquesApplication implements CommandLineRunner {

    @Autowired
    private AdminService adminService;
    @Autowired
    private SpectateurService spectateurService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ControleurService controleurService;
    @Autowired
    private OrganisateurService organisateurService;

    public static void main(String[] args) {
        SpringApplication.run(JeuxMiagiquesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Organisateur organisateur = adminService.creerOrganisateur("Admin", "root", "admin@root.fr", "orga");
        // Création d'une infrastructure sportive
        adminService.creerInfrastructureSportive("Stade Olympique", 10000, "1234 Rue Olympique");
        adminService.creerInfrastructureSportive("Stade Toulousain", 10000, "1234 Rue Olympique");

        // Création d'une épreuve
        Epreuve e = organisateurService.creerEpreuve("100m sprint", new Date(), 500, "Stade Olympique");
        organisateurService.creerEpreuve("test", new Date(), 1500, "Stade Toulousain");

        // Création d'un spectateur
        Spectateur s = spectateurService.inscription("Smith", "Anna", "anna.smith@example.com");

        // Création d'un participant
        organisateurService.creerDelegation("France");
        organisateurService.creerDelegation("USA");
        organisateurService.creerParticipant("Doe", "John", "john.doe@example.com");

        organisateurService.setDelegation("john.doe@example.com", "USA");

        // Création d'un contrôleur
        organisateurService.creerControleur("Brown", "Charlie", "charlie.brown@example.com");

        //organisateurService.supprimerEpreuve("100m sprint");
        //organisateurService.supprimerDelegation("USA");

        System.out.println(organisateurService.getTotalVentes());
        System.out.println(organisateurService.getTotalPlacesDisponibles());
        System.out.println(organisateurService.getChiffreAffaires());

    }
}
