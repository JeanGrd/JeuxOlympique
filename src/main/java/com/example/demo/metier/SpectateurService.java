package com.example.demo.metier;

import com.example.demo.dao.BilletRepository;
import com.example.demo.dao.SpectateurRepository;
import com.example.demo.dao.EpreuveRepository;
import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Billet reserverBillet(Billet billet) {
        billet.setEtat("Réservé");
        return billetRepository.save(billet);
    }

    public Billet annulerReservation(long billetId) {
        Optional<Billet> billet = billetRepository.findById(billetId);
        billet.ifPresent(b -> {
            b.setEtat("Annulé");
            billetRepository.save(b);
        });
        return billet.orElse(null);
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
}
