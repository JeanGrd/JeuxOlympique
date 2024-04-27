package com.example.demo.service;

import com.example.demo.dao.BilletRepository;
import com.example.demo.entities.Billet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SpectateurService {

    @Autowired
    private BilletRepository billetRepository;

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
}
