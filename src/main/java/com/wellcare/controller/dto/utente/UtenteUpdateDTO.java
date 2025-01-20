package com.wellcare.controller.dto.utente;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UtenteUpdateDTO {
    private String nome;
    private String cognome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascita;
    private String email;
    private String numTel;
    private String citta;
    private String cap;
    private String imgPath;

}
