package com.wellcare;

import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroCreationDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroViewDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareCreationDTO;
import com.wellcare.exceptions.EserciziGiornalieriException;
import com.wellcare.exceptions.SchedaAlimentareException;
import com.wellcare.model.*;
import com.wellcare.repository.cibo.CiboRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.contenutoSchedaAlimentare.ContenutoSchedaAlimentareRepository;
import com.wellcare.repository.eserciziGiornalieri.EserciziGiornalieriRepository;
import com.wellcare.repository.esercizio.EsercizioRepository;
import com.wellcare.repository.nutrizionista.NutrizionistaRepository;
import com.wellcare.repository.schedaAlimentare.SchedaAlimentareRepository;
import com.wellcare.repository.schedaAllenamento.SchedaAllenamentoRepository;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.service.eserciziGiornalieri.EserciziGiornalieriService;
import com.wellcare.service.eserciziGiornalieri.EserciziGiornalieriServiceImpl;
import com.wellcare.service.schedaAlimentare.SchedaAlimentareServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class TestInserimentoEserciziGiornalieri {

    EserciziGiornalieriRepository repository;
    ClienteRepository clienteRepository;
    EsercizioRepository esercizioRepository;
    SchedaAllenamentoRepository schedaAllenamentoRepository;

    EserciziGiornalieriService service;
    EsercizioGiornalieroCreationDTO dto;
    EsercizioGiornaliero esercizioGiornaliero;

    @Before
    public void setup()  {

        dto = new EsercizioGiornalieroCreationDTO();

        repository = Mockito.mock(EserciziGiornalieriRepository.class);
        clienteRepository=Mockito.mock(ClienteRepository.class);
        esercizioRepository=Mockito.mock(EsercizioRepository.class);
        schedaAllenamentoRepository=Mockito.mock(SchedaAllenamentoRepository.class);

        service = new EserciziGiornalieriServiceImpl(repository,clienteRepository,schedaAllenamentoRepository,esercizioRepository);

        esercizioGiornaliero=new EsercizioGiornaliero();
        esercizioGiornaliero.setData(new Date());
        esercizioGiornaliero.setRipetizioni(5);

        Cliente customer=new Cliente();
        customer.setAltezza(1.8F);
        customer.setPeso(100);
        customer.setUtente(new Utente());
        customer.setPrestNutrizionsita(null);
        customer.setPrestPersonalTrainer(null);
        customer.setId(1);
        esercizioGiornaliero.setCliente(customer);

        Esercizio esercizio=new Esercizio();
        esercizio.setId(33);
        esercizio.setNome("Addominali");
        esercizio.setDescrizione(null);
        esercizioGiornaliero.setEsercizio(esercizio);

        SchedaAllenamento scheda=new SchedaAllenamento();
        scheda.setNote(null);
        scheda.setCliente(customer);
        scheda.setDataCreazione(new Date());
        scheda.setId(30);
        esercizioGiornaliero.setSchedaAllenamento(scheda);
    }
    @Test
    public void Test_Case_1() {

        dto.setData(null);
        dto.setRipetizioni(5);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Data non valida!",ex.getMessage());

    }
    @Test
    public void Test_Case_2() {

        dto.setData(new Date());
        dto.setRipetizioni(0);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Ripetizioni non valide!",ex.getMessage());

    }
    @Test
    public void Test_Case_3() {

        dto.setData(new Date());
        dto.setRipetizioni(null);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Ripetizioni non valide!",ex.getMessage());

    }

    @Test
    public void Test_Case_4() {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(null);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Id esercizio non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_5() {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(0);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Id esercizio non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_6() {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(null);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Id scheda allenamento non valida!",ex.getMessage());

    }
    @Test
    public void Test_Case_7() {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(0);
        dto.setIdCliente(1);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Id scheda allenamento non valida!",ex.getMessage());

    }
    @Test
    public void Test_Case_8() {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(null);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Id cliente non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_9() {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(0);


        EserciziGiornalieriException ex= Assertions.assertThrows(EserciziGiornalieriException.class, ()-> service.createEsercizioGiornaliero(dto));

        Assert.assertEquals("Id cliente non valido!",ex.getMessage());

    }
    @Test
    public void Test_Case_10() throws EserciziGiornalieriException {

        dto.setData(new Date());
        dto.setRipetizioni(5);
        dto.setIdEsercizio(33);
        dto.setIdSchedaAllenamento(30);
        dto.setIdCliente(1);


        Mockito.when(service.entityToDTO(esercizioGiornaliero)).thenReturn(Mockito.any());
        Mockito.when(repository.save(esercizioGiornaliero)).thenReturn(esercizioGiornaliero);
        EsercizioGiornalieroViewDTO view=service.createEsercizioGiornaliero(dto);

        Assert.assertEquals(dto.getData(), view.getData());
        Assert.assertEquals(dto.getRipetizioni(), view.getRipetizioni());
        Assert.assertEquals(dto.getIdCliente(), view.getIdCliente());
        Assert.assertEquals(dto.getIdEsercizio(), view.getIdEsercizio());
        Assert.assertEquals(dto.getIdSchedaAllenamento(), view.getIdSchedaAllenamento());


    }
}
