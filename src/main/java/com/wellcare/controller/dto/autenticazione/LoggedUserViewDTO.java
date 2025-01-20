package com.wellcare.controller.dto.autenticazione;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import lombok.Data;

@Data
public class LoggedUserViewDTO {

    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String imgPath;
    private ClienteViewDTO cliente;
    private PersonalTrainerViewDTO personalTrainer;
    private NutrizionistaViewDTO nutrizionista;

}