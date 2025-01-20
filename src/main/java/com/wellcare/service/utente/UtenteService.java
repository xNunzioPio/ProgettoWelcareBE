package com.wellcare.service.utente;

import com.wellcare.controller.dto.password.PasswordChangeDTO;
import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.controller.dto.risorsa.FileStorageDownload;
import com.wellcare.controller.dto.utente.UtenteUpdateDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.exceptions.UtenteException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface  UtenteService {
        public UtenteViewDTO createUtente(RegistrationDTO dto) throws UtenteException;
        public UtenteViewDTO updateUtente(UtenteUpdateDTO dto, Integer id) throws UtenteException;
        public void removeUtente(Integer id);

        public byte[] uploadImage (MultipartFile multipartFile, Integer id) throws IOException, UtenteException;

        public FileStorageDownload readImage(Integer id) throws IOException;

        public List<UtenteViewDTO> findAll() throws UtenteException;
        public UtenteViewDTO findById(Integer id) throws UtenteException;
        public UtenteViewDTO findByEmail(String email) throws UtenteException;
        public void changePassword(PasswordChangeDTO passwordChange, boolean isPublic);
        public void generatePasswordAndSendEmail(String email);
        public List<UtenteViewDTO> findByNome(String nome) throws UtenteException;
        public List<UtenteViewDTO> findByCognome(String cognome) throws UtenteException;
        public List<UtenteViewDTO> findByCittà(String città) throws UtenteException;
        public UtenteViewDTO findByTelefono(String tel) throws UtenteException;
        public List<UtenteViewDTO> findByRuolo(Integer ruolo) throws UtenteException;

}
