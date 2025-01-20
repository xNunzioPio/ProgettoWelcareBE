package com.wellcare.controller;

import com.wellcare.controller.dto.autenticazione.AuthenticationViewDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.exceptions.UtenteException;
import com.wellcare.service.autenticazione.AuthenticationService;
import com.wellcare.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/authentication")
public class AutenticazioneController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public String login(@RequestBody AuthenticationViewDTO authDto) {
        return this.authenticationService.login(authDto.getEmail(), authDto.getPassword());
    }
}
