package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Operation {

    private long id_operation;
    private String type;
    private Timestamp date;
    private double montant;
}
