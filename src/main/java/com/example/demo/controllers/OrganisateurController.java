package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.metier.OrganisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @DeleteMapping("/delegation")
    public ResponseEntity<String> supprimerDelegation(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerDelegation(id);
            return ResponseEntity.ok("Délégation supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve")
    public ResponseEntity<Epreuve> creerEpreuve(@RequestBody Epreuve epreuve, @RequestParam Long idInfrastructure, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            epreuve.setDate(LocalDate.of(2025, 10, 2));
            Epreuve created = organisateurService.creerEpreuve(epreuve, idInfrastructure);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/epreuve")
    public ResponseEntity<String> supprimerEpreuve(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerEpreuve(id);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/participant")
    public ResponseEntity<Participant> creerParticipant(@RequestBody Participant participant, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            Participant created = organisateurService.creerParticipant(participant);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/participant")
    public ResponseEntity<String> supprimerParticipant(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerParticipant(id);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/controleur")
    public ResponseEntity<Controleur> creerControleur(@RequestBody Controleur controleur, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            Controleur created = organisateurService.creerControleur(controleur);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/controleur")
    public ResponseEntity<String> supprimerControleur(@RequestParam String email, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.supprimerControleur(email);
            return ResponseEntity.ok("Controleur supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/date")
    public ResponseEntity<String> setDate(@RequestParam LocalDate date, @RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.setDate(date, idEpreuve);
            return ResponseEntity.ok("Date mise à jour");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/nbParticipant")
    public ResponseEntity<String> setNbParticipant(@RequestParam long idEpreuve, @RequestParam int nbParticipant, HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.setNbParticipant(idEpreuve, nbParticipant);
            return ResponseEntity.ok("Nombrer de participant mis à jour");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/nbBillet")
    public ResponseEntity<String> setNbBillets(@RequestParam long idEpreuve, @RequestParam int nbBillets, HttpSession session) throws Exception {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.setNbBillets(idEpreuve, nbBillets);
            return ResponseEntity.ok("Nombre de billets mis à jour");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/participant/delegation")
    public ResponseEntity<String> setDelegation(@RequestParam String emailParticipant, @RequestParam long idDelegation, HttpSession session) throws Exception {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.setDelegation(emailParticipant, idDelegation);
            return ResponseEntity.ok("Delegation appliquée");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/resultat")
    public ResponseEntity<String> setResultat(@RequestBody Resultat resultat, @RequestParam long idEpreuve, @RequestParam String emailParticipant, HttpSession session) throws Exception {
        if (session.getAttribute("organisateurEmail") != null) {
            organisateurService.setResultat(resultat, idEpreuve, emailParticipant);
            return ResponseEntity.ok("Résultat mis à jour");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/places-disponibles")
    public ResponseEntity<String> getTotalPlacesDisponibles(HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            int placesDisponibles = organisateurService.getTotalPlacesDisponibles();
            return ResponseEntity.ok("Places disponible : " + placesDisponibles);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/chiffre-affaires")
    public ResponseEntity<String> getChiffreAffaires(HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            double chiffreAffaires = organisateurService.getChiffreAffaires();
            return ResponseEntity.ok("Chiffre d'affaires : " + chiffreAffaires);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/total-ventes")
    public ResponseEntity<String> getTotalVentes(HttpSession session) {
        if (session.getAttribute("organisateurEmail") != null) {
            int totalVentes= organisateurService.getTotalVentes();
            return ResponseEntity.ok("Total des ventes : " + totalVentes);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
