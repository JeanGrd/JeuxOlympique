package com.example.demo.controllers;

import com.example.demo.dto.EpreuveDTO;
import com.example.demo.dto.ResultatDTO;
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
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterOrganisateur(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    @PostMapping("/delegation")
    public ResponseEntity<String> creerDelegation(@RequestBody Delegation delegation, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.creerDelegation(delegation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Délégation créée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @DeleteMapping("/delegation")
    public ResponseEntity<String> supprimerDelegation(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerDelegation(id);
            return ResponseEntity.ok("Délégation supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve")
    public ResponseEntity<String> creerEpreuve(@RequestBody EpreuveDTO epreuveDTO, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.creerEpreuve(epreuveDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Epreuve créée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @DeleteMapping("/epreuve")
    public ResponseEntity<String> supprimerEpreuve(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerEpreuve(id);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PutMapping("/epreuve")
    public ResponseEntity<String> modifierEpreuve(@RequestBody EpreuveDTO epreuveDTO, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.modifierEpreuve(epreuveDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Epreuve modifiée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }


    @PostMapping("/participant")
    public ResponseEntity<String> creerParticipant(@RequestBody Participant participant, HttpSession session) {
        if (session.getAttribute("email") != null) {
            Participant created = organisateurService.creerParticipant(participant);
            return ResponseEntity.status(HttpStatus.CREATED).body("Participant créé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @DeleteMapping("/participant")
    public ResponseEntity<String> supprimerParticipant(@RequestParam long id, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerParticipant(id);
            return ResponseEntity.ok("Participant supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/controleur")
    public ResponseEntity<String> creerControleur(@RequestBody Controleur controleur, HttpSession session) {
        if (session.getAttribute("email") != null) {
            Controleur created = organisateurService.creerControleur(controleur);
            return ResponseEntity.status(HttpStatus.CREATED).body("Controleur créé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @DeleteMapping("/controleur")
    public ResponseEntity<String> supprimerControleur(@RequestParam String email, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerControleur(email);
            return ResponseEntity.ok("Controleur supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/date")
    public ResponseEntity<String> setDate(@RequestParam LocalDate date, @RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.setDate(date, idEpreuve);
            return ResponseEntity.ok("Date mise-à-jour.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/nbparticipants")
    public ResponseEntity<String> setNbParticipants(@RequestParam long idEpreuve, @RequestParam int nbParticipant, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = organisateurService.setNbParticipants(idEpreuve, nbParticipant);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/nbbillets")
    public ResponseEntity<String> setNbBillets(@RequestParam long idEpreuve, @RequestParam int nbBillets, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = organisateurService.setNbBillets(idEpreuve, nbBillets);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/participant/delegation")
    public ResponseEntity<String> setDelegation(@RequestParam String emailParticipant, @RequestParam long idDelegation, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.setDelegation(emailParticipant, idDelegation);
            return ResponseEntity.ok("Mise en relation entre le participant et la délagation réalisée avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @PostMapping("/epreuve/resultat")
    public ResponseEntity<String> setResultat(@RequestBody ResultatDTO resultatDTO, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.setResultat(resultatDTO);
            return ResponseEntity.ok("Résultat mis-à-jour");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/places-disponibles")
    public ResponseEntity<String> getTotalPlacesDisponibles(HttpSession session) {
        if (session.getAttribute("email") != null) {
            int placesDisponibles = organisateurService.getTotalPlacesDisponibles();
            return ResponseEntity.ok("Places disponibles : " + placesDisponibles);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/chiffre-affaires")
    public ResponseEntity<String> getChiffreAffaires(HttpSession session) {
        if (session.getAttribute("email") != null) {
            double chiffreAffaires = organisateurService.getChiffreAffaires();
            return ResponseEntity.ok("Chiffre d'affaires : " + chiffreAffaires);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    @GetMapping("/total-ventes")
    public ResponseEntity<String> getTotalVentes(HttpSession session) {
        if (session.getAttribute("email") != null) {
            int totalVentes = organisateurService.getTotalVentes();
            return ResponseEntity.ok("Total des ventes : " + totalVentes);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
