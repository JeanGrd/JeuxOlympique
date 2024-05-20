package com.example.demo.controllers;

import com.example.demo.metier.ControleurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/controleurs")
public class ControleurController {

    @Autowired
    private ControleurService controleurService;

    @PostMapping("/connexion")
    public ResponseEntity<String> connecterControleur(@RequestParam String email, HttpSession session) {
        boolean existe = controleurService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("controleurEmail", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterControleur(HttpSession session) {
        session.removeAttribute("controleurEmail");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    @PostMapping("/verifier-billet")
    public ResponseEntity<String> verifierBillet(@RequestParam long billetId, HttpSession session) {
        if (session.getAttribute("controleurEmail") != null) {
            boolean valide = controleurService.verifierBillet(billetId);
            if (valide) {
                return ResponseEntity.ok("Billet valide.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Billet invalide ou déjà utilisé.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
