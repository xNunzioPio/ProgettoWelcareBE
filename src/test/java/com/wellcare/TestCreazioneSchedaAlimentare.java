package com.wellcare;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareViewDTO;
import com.wellcare.exceptions.SchedaAlimentareException;
import com.wellcare.model.*;
import com.wellcare.repository.cibo.CiboRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.contenutoSchedaAlimentare.ContenutoSchedaAlimentareRepository;
import com.wellcare.repository.nutrizionista.NutrizionistaRepository;
import com.wellcare.repository.schedaAlimentare.SchedaAlimentareRepository;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.service.schedaAlimentare.SchedaAlimentareService;
import com.wellcare.service.schedaAlimentare.SchedaAlimentareServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.*;

public class TestCreazioneSchedaAlimentare {

    SchedaAlimentareService service;
    SchedaAlimentareRepository repository;
    ClienteRepository clienteRepository;
    CiboRepository ciboRepository;
    UtenteRepository utenteRepository;
    NutrizionistaRepository nutrizionistaRepository;
    ContenutoSchedaAlimentareRepository contSchedaRepo;
    SchedaAlimentareCreationDTO dto;
    List<ContenutoSchedaAlimentareCreationDTO> listContenutoSchedaAlimentare;
    ContenutoSchedaAlimentareCreationDTO conDto;
    SchedaAlimentare schedaAlimentare;
    Cliente customer;
    Utente user;
    ContenutoSchedaAlimentare contenutoSchedaAlimentare;


    @Before
    public void setup() throws SchedaAlimentareException {

        dto = new SchedaAlimentareCreationDTO();
        dto.setIdCliente(1);
        dto.setIdNutrizionista(7);

        listContenutoSchedaAlimentare=new ArrayList<>();
        conDto=new ContenutoSchedaAlimentareCreationDTO();
        repository = Mockito.mock(SchedaAlimentareRepository.class);
        clienteRepository=Mockito.mock(ClienteRepository.class);
        nutrizionistaRepository=Mockito.mock(NutrizionistaRepository.class);
        ciboRepository=Mockito.mock(CiboRepository.class);
        contSchedaRepo=Mockito.mock(ContenutoSchedaAlimentareRepository.class);
        utenteRepository=Mockito.mock(UtenteRepository.class);
        service = new SchedaAlimentareServiceImpl(utenteRepository,repository,clienteRepository,nutrizionistaRepository,ciboRepository,contSchedaRepo);

        user=new Utente();
        user.setId(5);
        user.setNome("Marco");
        user.setCognome("Prezioso");
        user.setEmail("marcoprezioso@gmail.com");
        user.setCitta("Roma");
        user.setCap("12345");
        user.setPassword("Ciao1234!");
        user.setNumTel("1234567891");
        user.setDataNascita(new Date());
        user.setImgPath(null);
        user.setRoles(new HashSet<>());

        customer=new Cliente();
        customer.setAltezza(1.8F);
        customer.setPeso(100);
        customer.setUtente(user);
        customer.setPrestNutrizionsita(null);
        customer.setPrestPersonalTrainer(null);
        customer.setId(1);

        Nutrizionista nutrizionista=new Nutrizionista();
        nutrizionista.setId(7);

        schedaAlimentare=new SchedaAlimentare();
        schedaAlimentare.setNote("Note del giorno ecc ecc");
        schedaAlimentare.setNutrizionista(nutrizionista);
        schedaAlimentare.setCliente(customer);
        schedaAlimentare.setId(1);

        contenutoSchedaAlimentare=new ContenutoSchedaAlimentare();
        contenutoSchedaAlimentare.setSchedaAlimentare(schedaAlimentare);
        contenutoSchedaAlimentare.setId(1);
        contenutoSchedaAlimentare.setCibo(new Cibo());
        contenutoSchedaAlimentare.setPrincipale(null);
        contenutoSchedaAlimentare.setPriorita("principale");
        contenutoSchedaAlimentare.setPasto("Colazione");
        contenutoSchedaAlimentare.setQuantita(100);
        contenutoSchedaAlimentare.setGiorno(1);



        Cibo cibo=new Cibo();
        cibo.setId(1);

        Mockito.when(utenteRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(nutrizionistaRepository.findById(Mockito.any())).thenReturn(Optional.of(nutrizionista));
        Mockito.when(ciboRepository.findById(Mockito.any())).thenReturn(Optional.of(cibo));
    }

    @Test
    public void Test_Case_1() throws SchedaAlimentareException {

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(null);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);

        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Giorno non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_2(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(0);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Giorno non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_4(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Pasto non valido",ex.getMessage());

    }
    @Test
    public void Test_Case_5(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("Col@zione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Pasto non valido",ex.getMessage());

    }
    @Test
    public void Test_Case_6(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(null);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Cibo non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_8(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(0);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Quantità non valida",ex.getMessage());

    }
    @Test
    public void Test_Case_9(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Priorità non valida",ex.getMessage());

    }
    @Test
    public void Test_Case_10(){

        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Prim@rio");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Priorità non valida",ex.getMessage());

    }
    @Test
    public void Test_Case_11(){

        dto.setNote("Note del giorno ecc ecc: ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Note non valide!",ex.getMessage());

    }
    @Test
    public void Test_Case_12(){

        dto.setNote("$&!");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);


        SchedaAlimentareException ex= Assertions.assertThrows(SchedaAlimentareException.class, ()-> service.createSchedaAlimentare(dto));

        Assert.assertEquals("Note non valide!",ex.getMessage());

    }
    @Test
    public void Test_Case_13() throws SchedaAlimentareException {



        dto.setNote("Note del giorno ecc ecc");

        conDto.setGiorno(1);
        conDto.setPasto("Colazione");
        conDto.setCibo(1);
        conDto.setQuantita(50);
        conDto.setPriorita("Primario");

        listContenutoSchedaAlimentare.add(conDto);
        dto.setContenutoScheda(listContenutoSchedaAlimentare);

        Mockito.when(repository.save(Mockito.any())).thenReturn(schedaAlimentare);
        Mockito.when(contSchedaRepo.save(Mockito.any())).thenReturn(contenutoSchedaAlimentare);
        Mockito.when(service.clienteEntityToDTO(customer)).thenReturn(Mockito.any());
        Mockito.when(utenteRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(service.entityToDTO(schedaAlimentare)).thenReturn(Mockito.any());
        Mockito.when(utenteRepository.findById(user.getId())).thenReturn(Optional.of(user));
        SchedaAlimentareViewDTO view=service.createSchedaAlimentare(dto);

        Assert.assertEquals(dto.getIdCliente(), view.getIdCliente());
        Assert.assertEquals(dto.getIdNutrizionista(), view.getIdNutrizionista());
        Assert.assertEquals(dto.getNote(), view.getNote());

    }
}
