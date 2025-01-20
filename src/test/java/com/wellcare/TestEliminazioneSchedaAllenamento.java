package com.wellcare;

import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoViewDTO;
import com.wellcare.exceptions.SchedaAllenamentoException;
import com.wellcare.model.SchedaAllenamento;
import com.wellcare.repository.schedaAllenamento.SchedaAllenamentoRepository;
import com.wellcare.service.schedaAllenamento.SchedaAllenamentoService;
import com.wellcare.service.schedaAllenamento.SchedaAllenamentoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

public class TestEliminazioneSchedaAllenamento {

    SchedaAllenamentoService service;
    SchedaAllenamentoRepository repository;
    SuccessResponseDTO success;

    @Before
    public void setup(){
        repository = Mockito.mock(SchedaAllenamentoRepository.class);
        service = new SchedaAllenamentoServiceImpl(repository);
    }
    @Test
    public void Test_Case_1(){
        Integer id=21323;

        SchedaAllenamento scheda=new SchedaAllenamento();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(scheda));

        SchedaAllenamentoException ex = Assertions.assertThrows(SchedaAllenamentoException.class, ()-> service.removeSchedaAllenamento(id));


        Assert.assertEquals("Scheda Allenamento non trovata!", ex.getMessage());
    }
    @Test
    public void Test_Case_2(){
        Integer id=null;

        SchedaAllenamento scheda=new SchedaAllenamento();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(scheda));

        SchedaAllenamentoException ex = Assertions.assertThrows(SchedaAllenamentoException.class, ()-> service.removeSchedaAllenamento(id));

        Assert.assertEquals("Scheda Allenamento non trovata!", ex.getMessage());
    }
    @Test
    public void Test_Case_3() throws SchedaAllenamentoException {
        Integer id=15;

        SchedaAllenamento scheda=new SchedaAllenamento();
        scheda.setId(15);
        //Mockito.when(repository.deleteById(Mockito.any())).thenReturn(0);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.ofNullable(scheda));

        success=service.removeSchedaAllenamento(id);

        Assert.assertEquals("Eliminazione effettuata con successo!", success.getMessage());
    }
}
