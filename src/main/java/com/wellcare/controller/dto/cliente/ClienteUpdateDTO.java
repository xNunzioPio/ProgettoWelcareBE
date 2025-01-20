package com.wellcare.controller.dto.cliente;


import lombok.Data;

@Data
public class ClienteUpdateDTO {

    private Integer peso;
    private Float altezza;
    private Integer prestazionePersonalTrainer;
    private Integer prestazioneNutrizionista;

}
