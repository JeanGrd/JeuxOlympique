package com.example.demo.dto;

import com.example.demo.entities.Epreuve;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResultatDTO {

    private String emailParticipant;
    private Long idEpreuve;
    private Integer point;
    private Integer position;

}
