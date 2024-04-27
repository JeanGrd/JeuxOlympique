package com.example.demo.controllers;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import com.example.demo.metier.SpectateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spectateurs")
public class SpectateurController {

    @Autowired
    private SpectateurService spectateurService;

    @PostMapping
    public ResponseEntity<Spectateur> inscrireSpectateur(@RequestBody Spectateur spectateur) {
        Spectateur created = spectateurService.creerSpectateur(spectateur.getNom(), spectateur.getPrenom(), spectateur.getEmail());
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerSpectateur(@PathVariable long id) {
        spectateurService.supprimerSpectateur(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/programme")
    public ResponseEntity<List<Epreuve>> consulterProgramme() {
        return ResponseEntity.ok(spectateurService.consulterProgramme());
    }

    @PostMapping("/billet")
    public ResponseEntity<Billet> reserverBillet(@RequestBody Billet billet) {
        return ResponseEntity.ok(spectateurService.reserverBillet(billet));
    }

    @PostMapping("/billet/{id}/annuler")
    public ResponseEntity<Billet> annulerReservation(@PathVariable long id) {
        return ResponseEntity.ok(spectateurService.annulerReservation(id));
    }
}
