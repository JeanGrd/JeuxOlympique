package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.metier.AdminService;
import com.example.demo.metier.SpectateurService;
import com.example.demo.metier.ParticipantService;
import com.example.demo.metier.ControleurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

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

    public static void main(String[] args) {
        SpringApplication.run(JeuxMiagiquesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Création d'une infrastructure sportive
        InfrastructureSportive infrastructure = adminService.creerInfrastructureSportive("Stade Olympique", 10000, "1234 Rue Olympique");

        // Création d'une épreuve
        Epreuve epreuve = adminService.creerEpreuve("100m sprint", new Date(), 500, infrastructure);

        // Création d'un spectateur
        Spectateur spectateur = adminService.creerSpectateur("Smith", "Anna", "anna.smith@example.com");

        // Création d'un participant
        Delegation delegation = adminService.creerDelegation("USA");
        Participant participant = adminService.creerParticipant("Doe", "John", "john.doe@example.com", delegation);

        // Création d'un contrôleur
        Controleur controleur = adminService.creerControleur("Brown", "Charlie", "charlie.brown@example.com");

        // Tentative de consulter le programme sans être connecté (simulé ici comme un appel direct de méthode)
        try {
            System.out.println("Tentative de consultation du programme sans connexion...");
            List<Epreuve> programme = spectateurService.consulterProgramme();
            System.out.println("Programme consulté : " + programme.size() + " épreuves listées.");
        } catch (Exception e) {
            System.out.println("Accès refusé : " + e.getMessage());
        }

        // Connexion du spectateur (simulation en mémoire)
        System.out.println("Connexion du spectateur avec l'email : " + spectateur.getEmail());
        boolean estConnecte = spectateurService.verifierEmailExist(spectateur.getEmail());
        if (estConnecte) {
            System.out.println("Spectateur connecté.");

            // Après la connexion, tentative de consulter le programme à nouveau
            List<Epreuve> programmeApresConnexion = spectateurService.consulterProgramme();
            System.out.println("Programme consulté après connexion : " + programmeApresConnexion.size() + " épreuves listées.");
        } else {
            System.out.println("Échec de la connexion, email non trouvé.");
        }

        // Connexion du participant (simulation en mémoire)
        System.out.println("Connexion du participant avec l'email : " + participant.getEmail());
        boolean estConnecteParticipant = participantService.verifierEmailExist(participant.getEmail());
        if (estConnecteParticipant) {
            System.out.println("Participant connecté.");

            // Après la connexion, tentative de lister les épreuves disponibles
            List<Epreuve> epreuvesDisponibles = participantService.listerEpreuvesDisponibles();
            System.out.println("Épreuves disponibles : " + epreuvesDisponibles.size() + " épreuves listées.");
        } else {
            System.out.println("Échec de la connexion, email non trouvé.");
        }

        // Connexion du contrôleur (simulation en mémoire)
        System.out.println("Connexion du contrôleur avec l'email : " + controleur.getEmail());
        boolean estConnecteControleur = controleurService.verifierEmailExist(controleur.getEmail());
        if (estConnecteControleur) {
            System.out.println("Contrôleur connecté.");

            // Après la connexion, tentative de vérifier un billet
            Billet billet = adminService.creerBillet(50.0, epreuve, spectateur);
            boolean billetValide = controleurService.verifierBillet(billet.getBillet_id());
            System.out.println("Billet vérifié : " + (billetValide ? "Valide" : "Invalide ou déjà utilisé"));
        } else {
            System.out.println("Échec de la connexion, email non trouvé.");
        }
    }
}
