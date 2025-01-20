package com.wellcare.validation;

import com.wellcare.controller.dto.password.PasswordChangeDTO;
import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorRegistration {
    @Autowired
    private UtenteRepository usersRepository;

    public void validateUserRegistration(RegistrationDTO userRegistration) {

        validatePasswordMatching(userRegistration.getPassword(), userRegistration.getRipetiPassword());
        validateEmail(userRegistration.getEmail());

    }

    private void validatePasswordMatching(String password, String repeatPassword) {

        if (!password.equals(repeatPassword)) {
            throw new RuntimeException("Le password non coincidono");
        }

    }

    private void validateEmail(String email) {

        if (usersRepository.existsByEmail(email)) {
            throw new RuntimeException("Email giÃ  presente nel portale");
        }

    }

    private void validateNotEmail(String email) {

        if (!usersRepository.existsByEmail(email)) {
            throw new RuntimeException("Email non trovata");
        }

    }

    public void validateUserOnChangePassword(PasswordChangeDTO passwordChange) {

        validateNotEmail(passwordChange.getEmail());
        validatePasswordMatching(passwordChange.getPassword(), passwordChange.getRepeatPassword());

    }
}