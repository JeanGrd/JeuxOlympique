package com.example.demo.controllers;

import com.example.demo.entities.Delegation;
import com.example.demo.entities.Epreuve;
import com.example.demo.metier.OrganisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Delegation> creerDelegation(@RequestBody String nom, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            Delegation created = organisateurService.creerDelegation(nom);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/delegation/{nom}")
    public ResponseEntity<String> supprimerDelegation(@PathVariable String nom, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerDelegation(nom);
            return ResponseEntity.ok("Délégation supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve")
    public ResponseEntity<Epreuve> creerEpreuve(@RequestParam String nom, @RequestParam LocalDate date,
                                                @RequestParam int nbPlaces, @RequestParam String infrastructure, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            Epreuve created = organisateurService.creerEpreuve(nom, date, nbPlaces, infrastructure);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/epreuve/{nom}")
    public ResponseEntity<String> supprimerEpreuve(@PathVariable String nom, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerEpreuve(nom);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @DeleteMapping("/participant/{email}")
    public ResponseEntity<String> supprimerParticipant(@PathVariable String email, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerParticipant(email);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @DeleteMapping("/controleur/{email}")
    public ResponseEntity<String> supprimerControleur(@PathVariable String email, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerControleur(email);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/places-disponibles")
    public ResponseEntity<Integer> getTotalPlacesDisponibles(HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            int placesDisponibles = organisateurService.getTotalPlacesDisponibles();
            return ResponseEntity.ok(placesDisponibles);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/chiffre-affaires")
    public ResponseEntity<Double> getChiffreAffaires(HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            double chiffreAffaires = organisateurService.getChiffreAffaires();
            return ResponseEntity.ok(chiffreAffaires);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/total-ventes")
    public ResponseEntity<Double> getTotalVentes(HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            double chiffreAffaires = organisateurService.getTotalVentes();
            return ResponseEntity.ok(chiffreAffaires);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
