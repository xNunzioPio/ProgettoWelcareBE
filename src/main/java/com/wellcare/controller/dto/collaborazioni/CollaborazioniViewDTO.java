package com.wellcare.controller.dto.collaborazioni;

import lombok.Data;

@Data
public class CollaborazioniViewDTO {

    private Integer idCliente;
    private String nominativoCliente;
    private Integer idPersonalTrainer;
    private String nominativoPersonalTrainer;
    private Integer idNutrizionista;
    private String nominativoNutrizionista;

}
