package com.example.demo.metier;

import com.example.demo.dao.BilletRepository;
import com.example.demo.dao.EpreuveRepository;
import com.example.demo.dao.SpectateurRepository;
import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpectateurService {

    private static final int MAX_BILLETS_PAR_EPREUVE = 4;
    @Autowired
    private BilletRepository billetRepository;
    @Autowired
    private SpectateurRepository spectateurRepository;
    @Autowired
    private EpreuveRepository epreuveRepository;

    public Spectateur inscription(String nom, String prenom, String email) {
        Spectateur spectateur = new Spectateur();
        spectateur.setNom(nom);
        spectateur.setPrenom(prenom);
        spectateur.setEmail(email);
        return spectateurRepository.save(spectateur);
    }

    @Transactional
    public void supprimerCompte(String email) {
        spectateurRepository.deleteByEmail(email);
    }

    public List<Epreuve> consulterProgramme() {
        Iterable<Epreuve> epreuves = epreuveRepository.findAll();
        return StreamSupport.stream(epreuves.spliterator(), false)
                .collect(Collectors.toList());
    }

    public String reserverBillet(long idEpreuve, String email) {
        Billet billet = new Billet();
        Spectateur spectateur = spectateurRepository.findByEmail(email).orElseThrow();
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow();
        billet.setSpectateur(spectateur);
        billet.setEpreuve(epreuve);
        billet.setPrix(epreuve.getPrix());

        if (nombreBilletsPourEpreuve(billet.getSpectateur(), billet.getEpreuve()) >= MAX_BILLETS_PAR_EPREUVE) {
            return "Vous avez déjà réservé le nombre maximum de billets pour cette épreuve.";
        }

        billet.setEtat("Réservé");
        billetRepository.save(billet);
        return "Réservation confirmée.";
    }

    public String payerBillet(long billetId) {
        Billet billet = billetRepository.findById(billetId).orElseThrow();
        if (Objects.equals(billet.getEtat(), "Réservé")) {
            billet.setEtat("Payé");
            billetRepository.save(billet);
            return "Paiement confirmée.";
        } else if (Objects.equals(billet.getEtat(), "Payé")) {
            return "Ce billet a déjà été payé";
        } else if (Objects.equals(billet.getEtat(), "Annulé")) {
            return "Ce billet a été annulé";
        } else {
            return "Une erreur est survenue sur votre billet";
        }
    }

    public String annulerReservation(long billetId, String email) {
        Billet billet = billetRepository.findByBilletIdAndSpectateur_Email(billetId, email).orElseThrow();
        if (Objects.equals(billet.getEtat(), "Payé")) {
            Epreuve epreuve = billet.getEpreuve();
            LocalDate dateActuelle = LocalDate.now();
            long joursAvantEpreuve = ChronoUnit.DAYS.between(dateActuelle, epreuve.getDate());
            double remboursement = calculerRemboursement(joursAvantEpreuve, billet.getPrix());

            billet.setEtat("Annulé");
            billet.setRemboursement(remboursement);
            billetRepository.save(billet);
            return "Votre billet est annulé et vous avez été remboursé de : " + remboursement + " €";
        } else if (Objects.equals(billet.getEtat(), "Réservé")) {
            billet.setEtat("Annulé");
            billetRepository.save(billet);
            return "Votre billet a été annulé avec succès";
        } else if (Objects.equals(billet.getEtat(), "Annulé")) {
            return "Votre billet a déjà été annulé";
        } else {
            return "Un problème est survenu sur votre billet";
        }
    }

    private double calculerRemboursement(long joursAvantEpreuve, double prix) {
        if (joursAvantEpreuve > 7) {
            return prix;
        } else if (joursAvantEpreuve >= 3) {
            return prix * 0.5;
        } else {
            return 0;
        }
    }

    private int nombreBilletsPourEpreuve(Spectateur spectateur, Epreuve epreuve) {
        return billetRepository.countAllBySpectateurAndEpreuve(spectateur, epreuve);
    }

    public boolean verifierEmailExist(String email) {
        return spectateurRepository.findByEmail(email).isPresent();
    }

    public String confirmationAnnulation(double montantRembourse) {
        return String.format("Votre annulation est confirmée. Vous serez remboursé de %.2f euros.", montantRembourse);
    }
}
