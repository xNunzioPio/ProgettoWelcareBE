package com.wellcare;

import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.controller.dto.utente.UtenteUpdateDTO;
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

import java.util.Optional;

public class TestModificaUtente {

    UtenteService service;
    UtenteRepository repository;
    PasswordEncoder encoder;
    UtenteUpdateDTO dto;
    SpecializzazioneService spService;

    @Before
    public void setup(){

        dto = new UtenteUpdateDTO();
        repository = Mockito.mock(UtenteRepository.class);
        encoder = Mockito.mock(PasswordEncoder.class);
        spService = Mockito.mock(SpecializzazioneService.class);


        service = new UtenteServiceImpl(repository, encoder, spService);
        Mockito.when(encoder.encode(Mockito.any())).thenReturn("");
        Mockito.doNothing().when(spService).specializzaUtente(Mockito.any(), Mockito.any());

        Utente utente=new Utente();
        utente.setId(1);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(utente));
    }



    @Test
    public void TC_1_1_1(){
        Integer id=null;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Id Utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_2(){
        Integer id=-2;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Id Utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_3(){

        Integer id=1;
        dto.setNome("");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Nome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_4(){
        Integer id=1;
        dto.setNome("Mi_!mmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Nome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_5(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Cognome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_6(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Ross!&%i");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Cognome utente non valido", ex.getMessage());

    }

    @Test
    public void TC_1_1_7(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com431");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Email non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_8(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimRos@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Email non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_9(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Numero di telefono non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_10(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("goal");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Numero di telefono non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_11(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("2233445566");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Numero di telefono non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_12(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Città non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_13(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Bellun@@");
        dto.setCap("32100");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Città non valida!", ex.getMessage());

    }

    @Test
    public void TC_1_1_14(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("12454782982");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Cap non valido!", ex.getMessage());

    }

    @Test
    public void TC_1_1_15(){
        Integer id=1;
        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("1b5a4");

        UtenteException ex = Assertions.assertThrows(UtenteException.class, ()-> service.updateUtente(dto,id));

        Assert.assertEquals("Cap non valido!", ex.getMessage());

    }

    @Test
    public void testUserUpdate() throws UtenteException {
        Integer id=1;
        Utente utente = new Utente();
        utente.setNome("Mimmo");
        utente.setCognome("Rossi");
        utente.setEmail("MimmoRossi@gmail.com");
        utente.setNumTel("0987654321");
        utente.setCitta("Belluno");
        utente.setCap("32100");

        dto.setNome("Mimmo");
        dto.setCognome("Rossi");
        dto.setEmail("MimmoRossi@gmail.com");
        dto.setNumTel("0987654321");
        dto.setCitta("Belluno");
        dto.setCap("32100");

        Mockito.when(repository.save(Mockito.any())).thenReturn(utente);

        UtenteViewDTO response = service.updateUtente(dto,id);

        Assert.assertEquals(dto.getNome(), response.getNome());
        Assert.assertEquals(dto.getCognome(), response.getCognome());
        Assert.assertEquals(dto.getEmail(), response.getEmail());
        Assert.assertEquals(dto.getNumTel(), response.getNumTel());
        Assert.assertEquals(dto.getCitta(), response.getCitta());
        Assert.assertEquals(dto.getCap(), response.getCap());


    }
}
