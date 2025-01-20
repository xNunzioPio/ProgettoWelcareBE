package com.wellcare.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wellcare.model.UserRole;
import com.wellcare.model.Utente;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.service.utente.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UtenteRepository userRepository;
    private final ObjectMapper mapper;



    public AuthorizationFilter(AuthenticationManager authenticationManager, UtenteRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader(JwtProvider.headerParam);
        if (header != null) {
            final DecodedJWT decoded = JwtProvider.verifyJwt(header.replace("Bearer ", ""));
            final ObjectNode userNode = this.mapper.readValue(decoded.getClaim("user").asString(), ObjectNode.class);
            final Utente user = this.mapper.convertValue(userNode, Utente.class);
            this.userRepository.findById(user.getId()).ifPresent(entity -> {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                entity.getRoles().stream().map(UserRole::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
                );
            });
        }
        chain.doFilter(request, response);
    }
}