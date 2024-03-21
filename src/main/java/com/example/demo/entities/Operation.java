package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue
    private long id_operation;
    private String type;
    private Timestamp date;
    private double montant;
}
