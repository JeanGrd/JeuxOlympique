package com.example.demo.jobs;

import com.example.demo.dao.*;
import com.example.demo.dto.EpreuveDTO;
import com.example.demo.dto.ResultatDTO;
import com.example.demo.entities.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour gérer les opérations liées aux organisateurs.
 * Ce service permet de gérer les délégations, les épreuves, les participants, les contrôleurs,
 * de définir les dates et le nombre de billets/participants pour les épreuves, et de consulter diverses statistiques.
 */
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
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ControleurRepository controleurRepository;
    @Autowired
    private ResultatRepository resultatRepository;

    /**
     * Vérifie si un email existe dans le système.
     *
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    public boolean verifierEmailExist(String email) {
        return organisateurRepository.findByEmail(email).isPresent();
    }

    /**
     * Crée une nouvelle délégation.
     *
     * @param delegation la délégation à créer
     */
    @Transactional
    public void creerDelegation(Delegation delegation) {
        delegationRepository.save(delegation);
    }

    /**
     * Supprime une délégation et dissocie tous les participants liés.
     *
     * @param id l'identifiant de la délégation à supprimer
     */
    @Transactional
    public void supprimerDelegation(long id) {
        Delegation delegation = delegationRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Délégation non trouvée avec l'id' : " + id));

        List<Participant> participants = participantRepository.findByDelegation(delegation);
        for (Participant participant : participants) {
            participant.setDelegation(null);
            participantRepository.save(participant);
        }
        delegationRepository.delete(delegation);
    }

    /**
     * Crée une nouvelle épreuve.
     *
     * @param epreuveDTO les informations de l'épreuve à créer
     * @return l'épreuve créée
     */
    @Transactional
    public Epreuve creerEpreuve(EpreuveDTO epreuveDTO) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNom(epreuveDTO.getNom());
        epreuve.setDate(epreuveDTO.getDate());
        epreuve.setNb_delegations(epreuveDTO.getNbDelegations());
        epreuve.setNb_billets(epreuveDTO.getNbBillets());
        epreuve.setPrix(epreuveDTO.getPrix());
        InfrastructureSportive infra = infrastructureSportiveRepository.findById(epreuveDTO.getIdInfrastructure()).orElseThrow(()
                -> new EntityNotFoundException("Infrastructure sportive non trouvée avec l'id : " + epreuveDTO.getIdInfrastructure()));
        epreuve.setInfrastructureSportive(infra);
        return epreuveRepository.save(epreuve);
    }

    /**
     * Supprime une épreuve.
     *
     * @param id l'identifiant de l'épreuve à supprimer
     */
    @Transactional
    public void supprimerEpreuve(long id) {
        Epreuve epreuve = epreuveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + id));

        epreuveRepository.delete(epreuve);
    }

    /**
     * Modifie les informations d'une épreuve.
     *
     * @param epreuveDTO les nouvelles informations de l'épreuve
     */
    @Transactional
    public void modifierEpreuve(EpreuveDTO epreuveDTO) {
        Epreuve epreuve = epreuveRepository.findById(epreuveDTO.getIdEpreuve())
                .orElseThrow(() -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + epreuveDTO.getIdEpreuve()));

        if (epreuveDTO.getIdInfrastructure() != null) {
            InfrastructureSportive infra = infrastructureSportiveRepository.findById(epreuveDTO.getIdInfrastructure())
                    .orElseThrow(() -> new EntityNotFoundException("Infrastructure sportive non trouvée avec l'id : " + epreuveDTO.getIdInfrastructure()));
            epreuve.setInfrastructureSportive(infra);
        }

        if (epreuveDTO.getNom() != null) {
            epreuve.setNom(epreuveDTO.getNom());
        }
        if (epreuveDTO.getDate() != null) {
            epreuve.setDate(epreuveDTO.getDate());
        }
        if (epreuveDTO.getNbDelegations() != null) {
            epreuve.setNb_delegations(epreuveDTO.getNbDelegations());
        }
        if (epreuveDTO.getNbBillets() != null) {
            epreuve.setNb_billets(epreuveDTO.getNbBillets());
        }
        if (epreuveDTO.getPrix() != null) {
            epreuve.setPrix(epreuveDTO.getPrix());
        }

        epreuveRepository.save(epreuve);
    }

    /**
     * Crée un nouveau participant.
     *
     * @param participant le participant à créer
     * @return le participant créé
     */
    @Transactional
    public Participant creerParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    /**
     * Supprime un participant.
     *
     * @param id l'identifiant du participant à supprimer
     */
    @Transactional
    public void supprimerParticipant(long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Participant non trouvé avec l'id : " + id));
        participantRepository.delete(participant);
    }

    /**
     * Crée un nouveau contrôleur.
     *
     * @param controleur le contrôleur à créer
     * @return le contrôleur créé
     */
    @Transactional
    public Controleur creerControleur(Controleur controleur) {
        return controleurRepository.save(controleur);
    }

    /**
     * Supprime un contrôleur par son email.
     *
     * @param email l'email du contrôleur à supprimer
     */
    @Transactional
    public void supprimerControleur(String email) {
        Controleur controleur = controleurRepository.findByEmail(email).orElseThrow(()
                -> new EntityNotFoundException("Contrôleur non trouvé avec l'email : " + email));
        controleurRepository.delete(controleur);
    }

    /**
     * Définit la date d'une épreuve.
     *
     * @param date      la nouvelle date de l'épreuve
     * @param idEpreuve l'identifiant de l'épreuve
     */
    @Transactional
    public void setDate(LocalDate date, long idEpreuve) {
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow(()
                -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + idEpreuve));
        epreuve.setDate(date);
        epreuveRepository.save(epreuve);
    }

    /**
     * Définit le nombre de participants pour une épreuve.
     *
     * @param idEpreuve     l'identifiant de l'épreuve
     * @param nbParticipant le nombre de participants
     * @return un message de confirmation
     */
    @Transactional
    public String setNbParticipants(long idEpreuve, int nbParticipant) {
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow(()
                -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + idEpreuve));;
        int tailleMax = epreuve.getInfrastructureSportive().getCapacite();
        if (nbParticipant > tailleMax) {
            return "Nombre de participants supérieur à la taille maximum de l'infrastructure.";
        }
        epreuve.setNb_delegations(nbParticipant);
        epreuveRepository.save(epreuve);
        return "Nombre de participant mis-à-jour.";
    }

    /**
     * Définit le nombre de billets pour une épreuve.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param nbBillets le nombre de billets
     * @return un message de confirmation
     */
    @Transactional
    public String setNbBillets(long idEpreuve, int nbBillets) {
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow(()
                -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + idEpreuve));;
        if (epreuve.getInfrastructureSportive().getCapacite() < nbBillets) {
            return "Nombre de billets supérieur à la taille maximum de l'infrastructure.";
        } else {
            epreuve.setNb_billets(nbBillets);
        }
        epreuveRepository.save(epreuve);
        return "Nombre de billets mis-à-jour.";
    }

    /**
     * Récupère le nombre total de places disponibles.
     *
     * @return le nombre total de places disponibles
     */
    public int getTotalPlacesDisponibles() {
        return epreuveRepository.getTotalPlacesDisponibles();
    }

    /**
     * Récupère le chiffre d'affaires total.
     *
     * @return le chiffre d'affaires total
     */
    public double getChiffreAffaires() {
        Double chiffreAffaires = epreuveRepository.getChiffreAffaires();
        return chiffreAffaires != null ? chiffreAffaires : 0.0;
    }

    /**
     * Récupère le nombre total de ventes.
     *
     * @return le nombre total de ventes
     */
    public int getTotalVentes() {
        return epreuveRepository.getTotalVentes();
    }

    /**
     * Associe un participant à une délégation.
     *
     * @param emailParticipant l'email du participant
     * @param idDelegation     l'identifiant de la délégation
     */
    @Transactional
    public void setDelegation(String emailParticipant, long idDelegation) {
        Participant participant = participantRepository.findByEmail(emailParticipant).orElseThrow(()
                -> new EntityNotFoundException("Participant non trouvé avec le l'email : " + emailParticipant));
        Delegation delegation = delegationRepository.findById(idDelegation).orElseThrow(()
                -> new EntityNotFoundException("Délégation non trouvée avec l'id : " + idDelegation));
        participant.setDelegation(delegation);
        participantRepository.save(participant);
    }

    /**
     * Enregistre le résultat d'un participant pour une épreuve.
     *
     * @param resultatDTO les informations du résultat
     */
    @Transactional
    public void setResultat(ResultatDTO resultatDTO) {
        Epreuve epreuve = epreuveRepository.findById(resultatDTO.getIdEpreuve()).orElseThrow(()
                -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + resultatDTO.getIdEpreuve()));
        Participant participant = participantRepository.findByEmail(resultatDTO.getEmailParticipant()).orElseThrow(()
                -> new EntityNotFoundException("Participant non trouvé avec l'email : " + resultatDTO.getEmailParticipant()));
        Resultat resultat = new Resultat();
        resultat.setPoint(resultatDTO.getPoint());
        resultat.setPosition(resultatDTO.getPosition());
        resultat.setEpreuve(epreuve);
        resultat.setParticipant(participant);

        resultatRepository.save(resultat);

        if (resultat.getPosition() >= 0 && resultat.getPosition() <= 2) {
            Delegation delegation = resultat.getParticipant().getDelegation();
            if (resultat.getPosition() == 0) {
                delegation.setNb_medaille_or(delegation.getNb_medaille_or() + 1);
            } else if (resultat.getPosition() == 1) {
                delegation.setNb_medaille_argent(delegation.getNb_medaille_argent() + 1);
            } else {
                delegation.setNb_medaille_bronze(delegation.getNb_medaille_bronze() + 1);
            }
            delegationRepository.save(delegation);
        }

    }

}
