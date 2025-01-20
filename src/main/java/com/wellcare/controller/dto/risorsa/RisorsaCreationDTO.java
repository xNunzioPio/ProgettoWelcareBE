

package com.wellcare.controller.dto.risorsa;


import lombok.Data;

@Data
public class RisorsaCreationDTO {

    private String nome;
    private String descrizione;
    private String path;
    private Integer idUtente;
    private Integer idTipo;

}
