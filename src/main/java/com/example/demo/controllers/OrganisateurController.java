package com.example.demo.controllers;

import com.example.demo.entities.Delegation;
import com.example.demo.entities.Epreuve;
import com.example.demo.metier.OrganisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/organisateurs")
public class OrganisateurController {

    @Autowired
    private OrganisateurService organisateurService;

    @PostMapping("/connexion")
    public ResponseEntity<String> connecterOrganisateur(@RequestParam String email, HttpSession session) {
        boolean existe = organisateurService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("organisateurEmail", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterOrganisateur(HttpSession session) {
        session.removeAttribute("organisateurEmail");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    @PostMapping("/delegation")
    public ResponseEntity<Delegation> creerDelegation(@RequestBody Delegation delegation, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            Delegation created = organisateurService.creerDelegation(delegation);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/delegation/{id}")
    public ResponseEntity<String> supprimerDelegation(@PathVariable long id, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerDelegation(id);
            return ResponseEntity.ok("Délégation supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve")
    public ResponseEntity<Epreuve> creerEpreuve(@RequestParam String nom, @RequestParam Date date,
                                                @RequestParam int nbPlaces, @RequestParam long infrastructureId, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            Epreuve created = organisateurService.creerEpreuve(nom, date, nbPlaces, infrastructureId);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/epreuve/{id}")
    public ResponseEntity<String> supprimerEpreuve(@PathVariable long id, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerEpreuve(id);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
