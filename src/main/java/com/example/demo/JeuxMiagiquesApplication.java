package com.example.demo;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Participant;
import com.example.demo.service.SpectateurService;
import com.example.demo.service.ParticipantService;
import com.example.demo.service.OrganisateurService;
import com.example.demo.service.ControleurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class JeuxMiagiquesApplication implements CommandLineRunner {

    @Autowired
    private SpectateurService spectateurService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private OrganisateurService organisateurService;

    @Autowired
    private ControleurService controleurService;

    public static void main(String[] args) {
        SpringApplication.run(JeuxMiagiquesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Exemple d'ajout d'une épreuve
        Epreuve epreuve = new Epreuve();
        epreuve.setNom("100m sprint");
        epreuve.setDate(new Date());
        epreuve.setNbPlaces(500);
        organisateurService.creerEpreuve(epreuve);

        // Exemple d'inscription d'un participant
        Participant participant = new Participant();
        participant.setNom("Doe");
        participant.setPrenom("John");
        participant.setEmail("john.doe@example.com");
        //participant = participantService.inscrireEpreuve(participant.getId(), epreuve.getId());

        // Exemple de réservation d'un billet
        Billet billet = new Billet();
        billet.setPrix(20);
        billet.setEpreuveId(epreuve.getId());
        billet.setSpectateurId(participant.getId()); // Supposons que le participant achète aussi des billets
        spectateurService.reserverBillet(billet);
    }
}
