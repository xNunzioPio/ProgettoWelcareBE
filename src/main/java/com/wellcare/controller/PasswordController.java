package com.wellcare.controller;

import com.wellcare.controller.dto.password.PasswordChangeDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "public/password")
public class PasswordController {

    @Autowired
    public UtenteService userService;

    @PostMapping(value = "/refresh")
    public ResponseEntity<SuccessResponseDTO> refreshPasswordUser(
            @RequestBody PasswordChangeDTO passwordChange
    ) {
        userService.changePassword(passwordChange, true);

        return new ResponseEntity<>(new SuccessResponseDTO("Password cambiata correttamente"), HttpStatus.OK);

    }

    @GetMapping(value = "/generate")
    public ResponseEntity<SuccessResponseDTO> generatePasswordUser(
            String email
    ) {

        userService.generatePasswordAndSendEmail(email);

        return new ResponseEntity<>(new SuccessResponseDTO("Password generata correttamente"), HttpStatus.OK);

    }

}