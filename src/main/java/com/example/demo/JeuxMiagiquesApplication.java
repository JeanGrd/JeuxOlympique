package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.metier.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class JeuxMiagiquesApplication implements CommandLineRunner {

    @Autowired
    private AdminService adminService;

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

        // Création et réservation d'un billet
        Billet billet = adminService.creerBillet(20, epreuve, spectateur);
    }
}
