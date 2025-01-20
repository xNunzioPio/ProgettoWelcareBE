package com.wellcare.controller.dto.registration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.util.Date;

@Data
public class RegistrationDTO {
    private String nome;
    private String cognome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascita;
    private String email;
    private String password;
    private String ripetiPassword;
    private String numTel;
    private String citta;
    private String cap;
    private ClienteCreationDTO cliente;
    private PersonalTrainerCreationDTO personalTrainer;
    private NutrizionistaCreationDTO nutrizionista;
}
