package com.wellcare.controller;


import com.wellcare.controller.dto.autenticazione.LoggedUserViewDTO;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.password.PasswordChangeDTO;
import com.wellcare.controller.dto.password.PasswordIdentityChangeDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.ClienteException;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.model.Utente;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.service.cliente.ClienteService;
import com.wellcare.service.nutrizionista.NutrizionistaService;
import com.wellcare.service.personalTrainer.PersonalTrainerService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import com.wellcare.controller.dto.utente.UtenteUpdateDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.exceptions.UtenteException;
import com.wellcare.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import com.wellcare.controller.dto.risorsa.FileStorageDownload;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;



@Controller
@RequestMapping(path = "private/utente")
public class UtenteController {

    @Autowired
    public UtenteService service;

    @Autowired
    public ClienteService clService;

    @Autowired
    public PersonalTrainerService ptService;

    @Autowired
    public NutrizionistaService ntService;
    @Autowired
    public UtenteRepository utenteRepository;

    @GetMapping(path="/all")
    public ResponseEntity<List<UtenteViewDTO>> getAllUser() throws UtenteException {
        List<UtenteViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }


    @GetMapping(path="/{id}")
    public ResponseEntity<UtenteViewDTO> getSpecificUser(@PathVariable(required = true, name = "id") int id) throws UtenteException {
        UtenteViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @GetMapping(path="/email/{email}")
    public ResponseEntity<UtenteViewDTO> getSpecificUserByEmail(@PathVariable(required = true, name = "email") String email) throws UtenteException {
        UtenteViewDTO viewDto = service.findByEmail(email);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @PostMapping(path="/update/{id}")
    public ResponseEntity<UtenteViewDTO> updateUser (@RequestBody UtenteUpdateDTO utente,@PathVariable(name = "id")int id) throws UtenteException {
        UtenteViewDTO viewDTO=service.updateUtente(utente,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }

    @DeleteMapping(path="/remove/{id}")
    public ResponseEntity<SuccessResponseDTO> removeUser(@PathVariable(required = true, name = "id") int id)  {
        service.removeUtente(id);
        return new ResponseEntity<>(new SuccessResponseDTO("Eliminazione con successo!"), HttpStatus.OK);
    }
    @PostMapping(value = "/refresh/password")
    public ResponseEntity<SuccessResponseDTO> refreshPasswordUser(
            @RequestBody PasswordIdentityChangeDTO passwordChange,
            Authentication authentication
    ) {

        LoggedUserViewDTO loggedUser = (LoggedUserViewDTO) authentication.getPrincipal();
        Utente user = utenteRepository.findByEmail(loggedUser.getEmail());
        PasswordChangeDTO pc = new PasswordChangeDTO(user.getEmail(), passwordChange.getOldPassword() , passwordChange.getPassword(), passwordChange.getRepeatPassword());
        service.changePassword(pc, false);

        return new ResponseEntity<>(new SuccessResponseDTO("Password changed successful"), HttpStatus.OK);

    }
    @GetMapping(value = "/my/session")
    public ResponseEntity<LoggedUserViewDTO> getUser(Authentication authentication) throws UtenteException, NutrizionistaException {

        LoggedUserViewDTO lgUserDTO = new LoggedUserViewDTO();

        Utente loggedUser = (Utente) authentication.getPrincipal();
        UtenteViewDTO user = service.findByEmail(loggedUser.getEmail());
        lgUserDTO.setId(user.getId());
        lgUserDTO.setNome(user.getNome());
        lgUserDTO.setCognome(user.getCognome());
        lgUserDTO.setEmail(user.getEmail());
        lgUserDTO.setImgPath(user.getImgPath());

        try {
            ClienteViewDTO cl = clService.findByIdUtente(user.getId());
            if(cl != null) {
                lgUserDTO.setCliente(cl);
            }
        } catch (ClienteException e){
            e.printStackTrace();
        }

        try {
            PersonalTrainerViewDTO pt = ptService.findByIdUtente(user.getId());
            if(pt != null) {
                lgUserDTO.setPersonalTrainer(pt);
            }
        } catch (PersonalTrainerException e){
            e.printStackTrace();
        }

        try {
            NutrizionistaViewDTO nt = ntService.findByIdUtente(user.getId());
            if(nt != null) {
                lgUserDTO.setNutrizionista(nt);
            }
        } catch (NutrizionistaException e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(lgUserDTO, HttpStatus.OK);

    }

    @PostMapping(value = "/uploadImage/{id}")
    public ResponseEntity<SuccessResponseDTO> uploadImage(@RequestPart("file") MultipartFile multipartFile, @PathVariable Integer id) throws IOException, UtenteException {

        byte[] array= multipartFile.getBytes();
        array = service.uploadImage(multipartFile, id);

        return new ResponseEntity<>(new SuccessResponseDTO("Immagine caricata correttamente"), HttpStatus.OK);
    }

    @GetMapping(value = "/readImage/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<Resource> readImage(@PathVariable Integer id) throws IOException {

        FileStorageDownload readingImage = service.readImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + readingImage.getFilename() + "\"");

        try {
            ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(readingImage.getByteArray()));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(readingImage.getByteArray().length)
                    .body(new InputStreamResource(new ByteArrayInputStream(readingImage.getByteArray())));
        } catch (IOException e) {
            throw new RuntimeException("Procedimento fallito");
        }

    }

}