package com.example.demo.controllers;

import com.example.demo.entities.Spectateur;
import com.example.demo.jobs.SpectateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spectateurs")
public class SpectateurController {

    @Autowired
    private SpectateurService spectateurService;

    @PostMapping("/connexion")
    public ResponseEntity<String> connecterSpectateur(@RequestParam String email, HttpSession session) {
        boolean existe = spectateurService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterSpectateur(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    @PostMapping
    public ResponseEntity<String> inscrire(@RequestBody Spectateur spectateur) {
        spectateurService.inscription(spectateur);
        return ResponseEntity.ok("Spectateur créé");
    }

    @DeleteMapping
    public ResponseEntity<String> supprimerCompte(HttpSession session) {
        if (session.getAttribute("email") != null) {
            spectateurService.supprimerCompte(session.getAttribute("email").toString());
            return ResponseEntity.ok("Compte bien supprimé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/programme")
    public ResponseEntity<?> consulterProgramme(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.consulterProgramme());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/billet")
    public ResponseEntity<?> reserverBillet(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.reserverBillet(id, session.getAttribute("email").toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/billet/payer")
    public ResponseEntity<?> payerBillet(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.payerBillet(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/billet/annuler")
    public ResponseEntity<?> annulerReservation(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.annulerReservation(id, session.getAttribute("email").toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
