
package com.wellcare.controller.dto.cliente;


import lombok.Data;

@Data
public class ClienteViewDTO {

    private Integer id;
    private String nominativo;
    private Integer idUtente;
    private Integer peso;
    private Float altezza;
    private Integer prestazioneNutrizionista;
    private Integer prestazionePersonalTrainer;

}
