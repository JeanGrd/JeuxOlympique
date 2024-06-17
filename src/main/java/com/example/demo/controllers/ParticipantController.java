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
            session.setAttribute("participantEmail", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterParticipant(HttpSession session) {
        session.removeAttribute("participantEmail");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    @GetMapping("/epreuves")
    public ResponseEntity<List<Epreuve>> listerEpreuves(HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            List<Epreuve> epreuves = participantService.listerEpreuvesDisponibles();
            return ResponseEntity.ok(epreuves);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

   @PostMapping("/desengager")
    public ResponseEntity<String> desengagerEpreuve(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            participantService.desengagerEpreuve(session.getAttribute("participantEmail").toString(), id);
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NOK");
        }
    }

    @PostMapping("/inscrire")
    public ResponseEntity<String> inscriptionEpreuve(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            participantService.inscrireEpreuve(session.getAttribute("participantEmail").toString(), id);
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NOK");
        }
    }

    @GetMapping("/resultats")
    public ResponseEntity<List<Resultat>> consulterResultatsParticipant(HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            List<Resultat> resultats = participantService.consulterResultatsParticipant(session.getAttribute("participantEmail").toString());
            return ResponseEntity.ok(resultats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/resultats/delegation")
    public ResponseEntity<List<Resultat>> consulterResultatsDelegation(HttpSession session) {
        if (session.getAttribute("participantEmail") != null) {
            List<Resultat> resultats = participantService.consulterResultatsParDelegation(session.getAttribute("participantEmail").toString());
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
