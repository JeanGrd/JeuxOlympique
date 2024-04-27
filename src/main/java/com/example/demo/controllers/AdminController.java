package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.metier.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/infrastructures")
    public ResponseEntity<InfrastructureSportive> createInfrastructure(@RequestBody InfrastructureSportive infrastructure) {
        InfrastructureSportive created = adminService.creerInfrastructureSportive(infrastructure.getNom(), infrastructure.getCapacite(), infrastructure.getAdresse());
        return ResponseEntity.ok(created);
    }

    @PostMapping("/epreuves")
    public ResponseEntity<Epreuve> createEpreuve(@RequestBody Epreuve epreuve) {
        Epreuve created = adminService.creerEpreuve(epreuve.getNom(), new Date(), epreuve.getNb_places(), epreuve.getInfrastructureSportive());
        return ResponseEntity.ok(created);
    }

    @PostMapping("/spectateurs")
    public ResponseEntity<Spectateur> createSpectateur(@RequestBody Spectateur spectateur) {
        Spectateur created = adminService.creerSpectateur(spectateur.getNom(), spectateur.getPrenom(), spectateur.getEmail());
        return ResponseEntity.ok(created);
    }

    @PostMapping("/billets")
    public ResponseEntity<Billet> createBillet(@RequestBody Billet billet) {
        Billet created = adminService.creerBillet(billet.getPrix(), billet.getEpreuve(), billet.getSpectateur());
        return ResponseEntity.ok(created);
    }
}
