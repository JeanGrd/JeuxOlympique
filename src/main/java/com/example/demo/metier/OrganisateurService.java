package com.example.demo.metier;

import com.example.demo.dao.EpreuveRepository;
import com.example.demo.dao.DelegationRepository;
import com.example.demo.dao.InfrastructureSportiveRepository;
import com.example.demo.dao.OrganisateurRepository;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Delegation;
import com.example.demo.entities.InfrastructureSportive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrganisateurService {

    @Autowired
    private EpreuveRepository epreuveRepository;
    @Autowired
    private DelegationRepository delegationRepository;
    @Autowired
    private InfrastructureSportiveRepository infrastructureSportiveRepository;
    @Autowired
    private OrganisateurRepository organisateurRepository;

    public Epreuve creerEpreuve(String nom, Date date, int nbPlaces, long infrastructureSportiveId) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNom(nom);
        epreuve.setDate(date);
        epreuve.setNb_places(nbPlaces);

        InfrastructureSportive infra = infrastructureSportiveRepository.findById(infrastructureSportiveId)
                .orElseThrow(() -> new RuntimeException("Infrastructure sportive non trouvée avec l'ID : " + infrastructureSportiveId));

        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }

    public boolean verifierEmailExist(String email) {
        return organisateurRepository.findByEmail(email).isPresent();
    }

    public Delegation creerDelegation(Delegation delegation) {
        return delegationRepository.save(delegation);
    }

    public void supprimerDelegation(long delegationId) {
        delegationRepository.deleteById(delegationId);
    }

    public void supprimerEpreuve(long epreuveId) {
        epreuveRepository.deleteById(epreuveId);
    }

}
