package com.wellcare.service.autenticazione;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wellcare.model.Utente;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UtenteRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        Utente user = this.userRepository.findByEmail(email);
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
        ObjectNode userNode = new ObjectMapper().convertValue(user, ObjectNode.class);
        userNode.remove("password");
        Map<String, Object> claimMap = new HashMap<>(0);
        claimMap.put("user", userNode);
        return JwtProvider.createJwt(email, claimMap);
    }
}
