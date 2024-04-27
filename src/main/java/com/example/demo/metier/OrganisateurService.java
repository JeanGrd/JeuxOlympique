package com.example.demo.service;

import com.example.demo.dao.EpreuveRepository;
import com.example.demo.dao.DelegationRepository;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Delegation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisateurService {

    @Autowired
    private EpreuveRepository epreuveRepository;

    @Autowired
    private DelegationRepository delegationRepository;

    public Epreuve creerEpreuve(Epreuve epreuve) {
        return epreuveRepository.save(epreuve);
    }

    public Delegation creerDelegation(Delegation delegation) {
        return delegationRepository.save(delegation);
    }

    public void supprimerEpreuve(long epreuveId) {
        epreuveRepository.deleteById(epreuveId);
    }
}
