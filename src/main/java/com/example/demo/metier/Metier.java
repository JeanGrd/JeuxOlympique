package com.example.demo.metier;
import com.example.demo.dao.ClientRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Metier {
    Client client;
    ClientRepository cr;

    public Metier(ClientRepository cr) {
        this.cr = cr;
    }

    public Client creerClient(String nom, String prenom) {
        client = new Client();

        client.setNom(nom);
        client.setPrenom(prenom);

        cr.save(client);

        return client;
    }

    public Compte ouvrirCompte(String nom, String prenom) {
        return null;
    }
}
