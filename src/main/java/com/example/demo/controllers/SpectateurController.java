package com.example.demo.controllers;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Spectateur;
import com.example.demo.metier.SpectateurService;
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
    public ResponseEntity<Spectateur> inscrire(@RequestBody Spectateur spectateur) {
        Spectateur created = spectateurService.inscription(spectateur.getNom(), spectateur.getPrenom(), spectateur.getEmail());
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerCompte(@PathVariable long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            spectateurService.supprimerCompte(id);
            return ResponseEntity.ok().build();
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
    public ResponseEntity<?> reserverBillet(@RequestBody Billet billet, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.reserverBillet(billet));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/billet/paiement")
    public ResponseEntity<?> payerBillet(@RequestBody Billet billet, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.payerBillet(billet));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/billet/{id}/annuler")
    public ResponseEntity<?> annulerReservation(@PathVariable long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.annulerReservation(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
