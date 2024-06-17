package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.metier.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

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

        /*
         *
         * Admin part
         *
         */

        Organisateur organisateur = adminService.creerOrganisateur("Admin", "root", "admin@root.fr", "orga");
        // Création d'une infrastructure sportive
        adminService.creerInfrastructureSportive("Stade Olympique", 10000, "1234 Rue Olympique");
        adminService.creerInfrastructureSportive("Stade Toulousain", 10000, "1234 Rue Olympique");

        /*
         *
         * Organisateur part 1
         *
         */

        // Création d'une épreuve
        Epreuve e = organisateurService.creerEpreuve("100m sprint", LocalDate.of(2025, 12, 2), 500, 300,115.5F, "Stade Olympique");
        organisateurService.creerEpreuve("test", LocalDate.of(2025, 12, 2), 1500, 300, 75, "Stade Toulousain");
        Participant Lans = organisateurService.creerParticipant("Robert", "Lans", "robert@lans.fr");
        Participant Roger = organisateurService.creerParticipant("Dons", "Roger", "dons@roger.fr");
        Participant Christina = organisateurService.creerParticipant("Garcia", "Christina", "garcia@christina.fr");

        // Création d'un participant
        organisateurService.creerDelegation("France");
        organisateurService.creerDelegation("USA");

        organisateurService.setDelegation("dons@roger.fr", "USA");
        organisateurService.setDelegation("garcia@christina.fr", "France");
        organisateurService.setDelegation("robert@lans.fr", "USA");

        // Mettre en place le nombre de billets
        organisateurService.setNbBillets(e.getEpreuveId(), 30);

        organisateurService.setNbParticipant(e.getEpreuveId(), 450);

        // Création d'un contrôleur
        organisateurService.creerControleur("Brown", "Charlie", "charlie.brown@example.com");

        /*
         *
         * Spectateur part
         *
         */

        // Use case: Il peut s’inscrire sur l’application
        Spectateur Ana = spectateurService.inscription("Palea", "Ana", "palea@ana.fr");
        Spectateur Jean = spectateurService.inscription("Guiraud", "Jean", "guiraud@jean.fr");
        Spectateur Touria = spectateurService.inscription("Sayagh", "Touria", "sayagh@touria.fr");

        // Use case: Consulter le programme des épreuves
        System.out.println(spectateurService.consulterProgramme());

        // Use case: Supprimer son compte
        spectateurService.supprimerCompte(Ana.getEmail());

        // Use case: Réserver des billets pour assister aux épreuves
        spectateurService.reserverBillet(e.getEpreuveId(), Jean.getEmail());
        spectateurService.reserverBillet(e.getEpreuveId(), Jean.getEmail());

        // Use case: payer en ligne
        spectateurService.payerBillet(1);

        //Use case: Annuler une réservation
        spectateurService.annulerReservation(2, Jean.getEmail());

        /*
         *
         * Participant part
         *
         */

        // Use case: Consulter les épreuves disponibles
        System.out.println(participantService.consulterProgramme());

        // Use case: S'inscrire à des épreuves au nom de sa délégation
        participantService.inscrireEpreuve(Roger.getEmail(), e.getEpreuveId());
        participantService.inscrireEpreuve(Christina.getEmail(), e.getEpreuveId());

        participantService.desengagerEpreuve(Roger.getEmail(), e.getEpreuveId());

        // Use case: consulter ses résultats
        System.out.println(participantService.consulterResultatsParticipant(Roger.getEmail()));

        // Use case: classement de sa délégation
        System.out.println(participantService.consulterResultatsParDelegation(Roger.getEmail()));

        /*
         *
         * Contrôleur part
         *
         */

        controleurService.verifierBillet(1);
        //organisateurService.supprimerEpreuve("100m sprint");
        //organisateurService.supprimerDelegation("USA");

        organisateurService.setResultat(12, 1, e.getEpreuveId(), Roger.getEmail());

        System.out.println(organisateurService.getTotalVentes());
        System.out.println(organisateurService.getTotalPlacesDisponibles());
        System.out.println(organisateurService.getChiffreAffaires());

    }
}
