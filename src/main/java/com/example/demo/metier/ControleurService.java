package com.example.demo.metier;

import com.example.demo.dao.BilletRepository;
import com.example.demo.dao.ControleurRepository;
import com.example.demo.entities.Billet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ControleurService {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private ControleurRepository controleurRepository;

    public boolean verifierEmailExist(String email) {
        return controleurRepository.findByEmail(email).isPresent();
    }

    public boolean verifierBillet(long billetId) {
        Optional<Billet> billet = billetRepository.findById(billetId);
        return billet.map(b -> "Réservé".equals(b.getEtat())).orElse(false);
    }
}
