package com.example.demo;

import com.example.demo.dto.EpreuveDTO;
import com.example.demo.dto.ResultatDTO;
import com.example.demo.entities.*;
import com.example.demo.jobs.*;
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

    /**
     * Cette partie permet à la fois d'initialiser la base de données,
     * mais aussi de tester les fonctions directement depuis les services.
     *
     * Note : elle ne couvre pas tous les cas, merci de ne pas se baser sur les fonctions ci-dessous.
     */
    @Override
    public void run(String... args) {

        /*
         *
         * Admin part
         *
         */

        Organisateur organisateur = new Organisateur();
        organisateur.setPrenom("Admin");
        organisateur.setNom("Root");
        organisateur.setRole("organiser");
        organisateur.setEmail("admin@root.fr");

        adminService.creerOrganisateur(organisateur);
        // Création d'une infrastructure sportive
        InfrastructureSportive stadeOlympique = new InfrastructureSportive();
        stadeOlympique.setNom("Stade Olympique");
        stadeOlympique.setAdresse("1234 Rue Olympique");
        stadeOlympique.setCapacite(10000);

        InfrastructureSportive stadeToulousain = new InfrastructureSportive();
        stadeToulousain.setNom("Stade Toulousain");
        stadeToulousain.setAdresse("1234 Rue Olympique");
        stadeToulousain.setCapacite(5000);

        adminService.creerInfrastructureSportive(stadeOlympique);
        adminService.creerInfrastructureSportive(stadeToulousain);

        /*
         *
         * Organisateur part 1
         *
         */

        // Création d'une épreuve
        EpreuveDTO epreuveDTO1 = new EpreuveDTO();
        epreuveDTO1.setNom("300m sprint");
        epreuveDTO1.setPrix(135.5);
        epreuveDTO1.setNbBillets(1500);
        epreuveDTO1.setDate(LocalDate.of(2025, 10, 2));
        epreuveDTO1.setNbDelegations(15);
        epreuveDTO1.setIdInfrastructure(stadeToulousain.getId());

        EpreuveDTO epreuveDTO2 = new EpreuveDTO();
        epreuveDTO2.setNom("100m sprint");
        epreuveDTO2.setPrix(115.5);
        epreuveDTO2.setNbBillets(1000);
        epreuveDTO2.setDate(LocalDate.of(2025, 12, 2));
        epreuveDTO2.setNbDelegations(10);
        epreuveDTO2.setIdInfrastructure(stadeOlympique.getId());

        Epreuve e = organisateurService.creerEpreuve(epreuveDTO1);
        organisateurService.creerEpreuve(epreuveDTO2);

        Participant Lans = new Participant();
        Lans.setPrenom("Lans");
        Lans.setNom("Robert");
        Lans.setEmail("robert@lans.fr");

        Participant Roger = new Participant();
        Roger.setPrenom("Roger");
        Roger.setNom("Dons");
        Roger.setEmail("dons@roger.fr");

        Participant Christina = new Participant();
        Christina.setPrenom("Christina");
        Christina.setNom("Garcia");
        Christina.setEmail("garcia@christina.fr");

        organisateurService.creerParticipant(Lans);
        organisateurService.creerParticipant(Roger);
        organisateurService.creerParticipant(Christina);

        Delegation france = new Delegation();
        france.setNom("France");
        Delegation usa = new Delegation();
        usa.setNom("USA");

        // Création d'un participant
        organisateurService.creerDelegation(france);
        organisateurService.creerDelegation(usa);
        organisateurService.setDelegation("dons@roger.fr", 1);
        organisateurService.setDelegation("garcia@christina.fr", 2);
        organisateurService.setDelegation("robert@lans.fr", 1);

        // Mettre en place le nombre de billets
        organisateurService.setNbBillets(e.getId(), 30);

        organisateurService.setNbParticipants(e.getId(), 450);

        // Création d'un contrôleur
        Controleur c = new Controleur();
        c.setPrenom("Cédric");
        c.setNom("Teyssié");
        c.setEmail("cédri@teyssié.fr");

        organisateurService.creerControleur(c);

        /*
         *
         * Spectateur part
         *
         */

        // Use case: Il peut s’inscrire sur l’application

        Spectateur Ana = new Spectateur();
        Ana.setPrenom("Ana");
        Ana.setNom("Palea");
        Ana.setEmail("palea@ana.fr");

        Spectateur Jean = new Spectateur();
        Jean.setPrenom("Jean");
        Jean.setNom("Guiraud");
        Jean.setEmail("guiraud@jean.fr");

        Spectateur Touria = new Spectateur();
        Touria.setPrenom("Touria");
        Touria.setNom("Sayagh");
        Touria.setEmail("sayagh@touria.fr");

        spectateurService.inscription(Ana);
        spectateurService.inscription(Jean);
        spectateurService.inscription(Touria);

        // Use case: Consulter le programme des épreuves
        System.out.println("Programme : " + spectateurService.consulterProgramme());

        // Use case: Supprimer son compte
        spectateurService.supprimerCompte(Ana.getEmail());

        // Use case: Réserver des billets pour assister aux épreuves
        spectateurService.reserverBillet(e.getId(), Jean.getEmail());
        spectateurService.reserverBillet(e.getId(), Jean.getEmail());

        // Use case: payer en ligne
        spectateurService.payerBillet(1, Jean.getEmail());

        //Use case: Annuler une réservation
        spectateurService.annulerReservation(2, Jean.getEmail());

        /*
         *
         * Participant part
         *
         */

        // Use case: Consulter les épreuves disponibles
        System.out.println("Liste des épreuves : " + participantService.consulterEpreuveDisponible());

        // Use case: S'inscrire à des épreuves au nom de sa délégation
        participantService.inscrireEpreuve(Roger.getEmail(), e.getId());
        participantService.inscrireEpreuve(Christina.getEmail(), e.getId());

        participantService.desengagerEpreuve(Roger.getEmail(), e.getId());

        // Use case: consulter ses résultats
        System.out.println("Résultat du participant Roger : " + participantService.consulterResultatsParticipant(Roger.getEmail()));

        // Use case: classement de sa délégation
        System.out.println("Résultat de la délégation de Roger : " + participantService.consulterResultatsParDelegation(Roger.getEmail()));

        /*
         *
         * Contrôleur part
         *
         */

        controleurService.verifierBillet(1);

        /*
         *
         * Organisateur part 2
         *
         */

        ResultatDTO resultat = new ResultatDTO();
        resultat.setPosition(1);
        resultat.setPoint(12);
        resultat.setIdEpreuve(e.getId());
        resultat.setEmailParticipant("garcia@christina.fr");
        organisateurService.setResultat(resultat);

        System.out.println("Total des ventes : " + organisateurService.getTotalVentes());
        System.out.println("Total des places disponibles : " + organisateurService.getTotalPlacesDisponibles());
        System.out.println("Chiffre d'affaires : " + organisateurService.getChiffreAffaires());

        System.err.println("Server is running!");

    }
}
