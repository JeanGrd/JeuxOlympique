package com.example.demo.controllers;

import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Resultat;
import com.example.demo.metier.ParticipantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping("/connexion")
    public ResponseEntity<String> connecterParticipant(@RequestParam String email, HttpSession session) {
        boolean existe = participantService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterParticipant(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    @PostMapping("/inscrire")
    public ResponseEntity<String> inscriptionEpreuve(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = participantService.inscrireEpreuve(session.getAttribute("email").toString(), id);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Une erreur est survenue");
        }
    }

   @PostMapping("/desengager")
    public ResponseEntity<String> desengagerEpreuve(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = participantService.desengagerEpreuve(session.getAttribute("email").toString(), id);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Une erreur est survenue");
        }
    }

    @GetMapping("/resultats")
    public ResponseEntity<List<Resultat>> consulterResultatsParticipant(HttpSession session) {
        if (session.getAttribute("email") != null) {
            List<Resultat> resultats = participantService.consulterResultatsParticipant(session.getAttribute("email").toString());
            return ResponseEntity.ok(resultats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/resultats/delegation")
    public ResponseEntity<List<Resultat>> consulterResultatsDelegation(HttpSession session) {
        if (session.getAttribute("email") != null) {
            List<Resultat> resultats = participantService.consulterResultatsParDelegation(session.getAttribute("email").toString());
            return ResponseEntity.ok(resultats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/programme")
    public ResponseEntity<?> consulterProgramme(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(participantService.consulterProgramme());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

}
