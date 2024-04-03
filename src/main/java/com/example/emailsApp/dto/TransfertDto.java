package com.example.emailsApp.dto;

import lombok.Data;

@Data
public class TransfertDto {
    private String compteSource;
    private String compteDestinataire;
    private double montant;
    private String description;
}
