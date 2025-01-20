package com.wellcare;


import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.exceptions.UtenteException;
import com.wellcare.model.Utente;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.service.specializzazione.SpecializzazioneService;
import com.wellcare.service.utente.UtenteService;
import com.wellcare.service.utente.UtenteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestCreazioneUtente {

    UtenteService service;
    UtenteRepository repository;
    RegistrationDTO dto;
    PasswordEncoder encoder;
    SpecializzazioneService spService;

    @Before
    public void setup(){

        dto = new RegistrationDTO();
        repository = Mockito.mock(UtenteRepository.class);
        encoder = Mockito.mock(PasswordEncoder.class);
        spService = Mockito.mock(SpecializzazioneService.class);

        service = new UtenteServiceImpl(repository, encoder, spService);
        Mockito.when(encoder.encode(Mockito.any())).thenReturn("");
        Mockito.doNothing().when(spService).specializzaUtente(Mockito.any(), Mockito.any());
    }



    @Test
    public void TC_1_1_1(){

        dto.setNome("");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Nome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_2(){

        dto.setNome("Genn@ro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Nome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_3(){

        dto.setNome("Gennaro");
        dto.setCognome("");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Cognome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_4(){

        dto.setNome("Gennaro");
        dto.setCognome("Esp_sito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Cognome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_5(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("gennaro@gmail.com123");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Email non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_6(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("genny.esp@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Email non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_7(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231query1929302poiuyt1");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Password non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_8(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass 123 1");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Password non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_9(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Numero di telefono non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_10(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("querty");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Numero di telefono non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_11(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("0001112223");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Numero di telefono non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_12(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Città non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_13(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otr@nto");
        dto.setCap("12454");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Città non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_14(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454782982");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Cap non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_15(){

        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("1b5a4");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.createUtente(dto));

        Assert.assertEquals("Cap non valido!", ex.getMessage());

    }

    @Test
    public void testUserRegistration() throws UtenteException {

        Utente utente = new Utente();
        utente.setNome("Gennaro");
        utente.setCognome("Esposito");
        utente.setEmail("GennaroEsposito@gmail.com");
        utente.setPassword("Pass1231");
        utente.setNumTel("1234567890");
        utente.setCitta("Otranto");
        utente.setCap("12454");


        dto.setNome("Gennaro");
        dto.setCognome("Esposito");
        dto.setEmail("GennaroEsposito@gmail.com");
        dto.setPassword("Pass1231");
        dto.setNumTel("1234567890");
        dto.setCitta("Otranto");
        dto.setCap("12454");

        Mockito.when(repository.save(Mockito.any())).thenReturn(utente);
        //Mockito.when(repository.save(Mockito.any())).thenReturn(utente);

        UtenteViewDTO response = service.createUtente(dto);

        Assert.assertEquals(dto.getNome(), response.getNome());
        Assert.assertEquals(dto.getPassword(), response.getPassword());
        Assert.assertEquals(dto.getEmail(), response.getEmail());
        Assert.assertEquals(dto.getPassword(), response.getPassword());
        Assert.assertEquals(dto.getNumTel(), response.getNumTel());
        Assert.assertEquals(dto.getCitta(), response.getCitta());
        Assert.assertEquals(dto.getCap(), response.getCap());


    }


}
