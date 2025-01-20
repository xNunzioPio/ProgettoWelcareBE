

package com.wellcare.controller.dto.risorsa;


import lombok.Data;

@Data
public class RisorsaViewDTO {

    private Integer id;
    private String nome;
    private String descrizione;
    private String path;
    private Integer idUtente;
    private Integer idTipo;

}
