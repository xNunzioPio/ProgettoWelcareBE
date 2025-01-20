package com.wellcare.service.cliente;

import com.wellcare.controller.dto.cliente.RichiestaPrestazioneNutrizionistaDTO;
import com.wellcare.controller.dto.cliente.RichiestaPrestazionePersonalTrainerDTO;
import com.wellcare.controller.dto.collaborazioni.CollaborazioniViewDTO;
import com.wellcare.controller.dto.registration.ClienteCreationDTO;
import com.wellcare.controller.dto.cliente.ClienteUpdateDTO;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.exceptions.ClienteException;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface ClienteService {

    public ClienteViewDTO createCliente(ClienteCreationDTO dto);
    public ClienteViewDTO updateCliente(ClienteUpdateDTO dto, Integer id)throws ClienteException;


    public List<ClienteViewDTO> findAll() throws ClienteException;
    public ClienteViewDTO findById(Integer id) throws ClienteException;
    public ClienteViewDTO findByIdUtente(Integer id) throws ClienteException;
    public CollaborazioniViewDTO getCollaborationsByIdCliente(Integer idCliente) throws ClienteException;
    public void richiestaPrestazionePT(RichiestaPrestazionePersonalTrainerDTO dto, Integer idCliente) throws PersonalTrainerException;
    public void richiestaPrestazioneNutr(RichiestaPrestazioneNutrizionistaDTO dto, Integer idCliente) throws NutrizionistaException;


}
