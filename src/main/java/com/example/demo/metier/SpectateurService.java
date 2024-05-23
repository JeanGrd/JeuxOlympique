package com.example.demo.metier;

import com.example.demo.dao.BilletRepository;
import com.example.demo.dao.SpectateurRepository;
import com.example.demo.dao.EpreuveRepository;
import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpectateurService {

    @Autowired
    private BilletRepository billetRepository;
    @Autowired
    private SpectateurRepository spectateurRepository;
    @Autowired
    private EpreuveRepository epreuveRepository;

    private static final int MAX_BILLETS_PAR_EPREUVE = 4;
    private double remboursement;

    public String reserverBillet(Billet billet) {
        if (nombreBilletsPourEpreuve(billet.getSpectateur(), billet.getEpreuve()) >= MAX_BILLETS_PAR_EPREUVE) {
            return "Vous avez déjà réservé le nombre maximum de billets pour cette épreuve.";
        }
        billet.setEtat("Réservé");
        billetRepository.save(billet);
        return "Réservation confirmée.";
    }

    public Billet annulerReservation(long billetId) {
        Optional<Billet> billetOptional = billetRepository.findById(billetId);
        if (billetOptional.isPresent()) {
            Billet billet = billetOptional.get();
            LocalDate dateActuelle = LocalDate.now();
            long joursAvantEpreuve = ChronoUnit.DAYS.between(dateActuelle, billet.getDateValidite());
            this.remboursement = calculerRemboursement(joursAvantEpreuve, billet.getPrix());

            billet.setEtat("Annulé");
            billetRepository.save(billet);
            return billet;
        }
        return null;
    }

    private double calculerRemboursement(long joursAvantEpreuve, double prix) {
        if (joursAvantEpreuve > 7) {
            return prix; // Remboursement intégral
        } else if (joursAvantEpreuve >= 3) {
            return prix * 0.5; // Remboursement de 50%
        } else {
            return 0; // Aucun remboursement
        }
    }

    private int nombreBilletsPourEpreuve(Spectateur spectateur, Epreuve epreuve) {
        return billetRepository.countAllBySpectateurAndEpreuve(spectateur, epreuve);
    }

    public Spectateur creerSpectateur(String nom, String prenom, String email) {
        Spectateur spectateur = new Spectateur();
        spectateur.setNom(nom);
        spectateur.setPrenom(prenom);
        spectateur.setEmail(email);
        return spectateurRepository.save(spectateur);
    }

    public void supprimerSpectateur(long id) {
        spectateurRepository.deleteById(id);
    }

    public List<Epreuve> consulterProgramme() {
        Iterable<Epreuve> epreuves = epreuveRepository.findAll();
        return StreamSupport.stream(epreuves.spliterator(), false)
                .collect(Collectors.toList());
    }

    public boolean verifierEmailExist(String email) {
        return spectateurRepository.findByEmail(email).isPresent();
    }

    public String confirmationAnnulation(double montantRembourse) {
        return String.format("Votre annulation est confirmée. Vous serez remboursé de %.2f euros.", montantRembourse);
    }
}
