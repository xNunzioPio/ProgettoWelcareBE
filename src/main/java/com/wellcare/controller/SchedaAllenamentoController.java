package com.wellcare.controller;

import com.wellcare.controller.dto.cibo.CiboViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoViewDTO;
import com.wellcare.controller.dto.esercizio.EsercizioViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoCreationDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAlimentareException;
import com.wellcare.exceptions.ContenutoSchedaAllenamentoException;
import com.wellcare.exceptions.EsercizioException;
import com.wellcare.exceptions.SchedaAllenamentoException;
import com.wellcare.service.schedaAllenamento.ContenutoSchedaAllenamentoService;
import com.wellcare.service.schedaAllenamento.EsercizioService;
import com.wellcare.service.schedaAllenamento.SchedaAllenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/private/schedaAllenamento")
public class SchedaAllenamentoController {

    @Autowired
    public SchedaAllenamentoService service;
    @Autowired
    public ContenutoSchedaAllenamentoService contenutoSchedaAllenamentoService;
    @Autowired
    public EsercizioService esercizioService;


    @GetMapping(path="/all")
    public ResponseEntity<List<SchedaAllenamentoViewDTO>> getAllTrainingSheet() throws SchedaAllenamentoException {
        List<SchedaAllenamentoViewDTO> ListDto = service.getAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<SchedaAllenamentoViewDTO> getSpecificTrainingSheet(@PathVariable(required = true, name = "id") int id) throws SchedaAllenamentoException {
        SchedaAllenamentoViewDTO viewDto = service.getById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @PostMapping(path="/create")
    public ResponseEntity<SchedaAllenamentoViewDTO> createTrainingSheet(@RequestBody SchedaAllenamentoCreationDTO scheda) throws ContenutoSchedaAllenamentoException {
        SchedaAllenamentoViewDTO viewDTO=service.createSchedaAllenamento(scheda);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @DeleteMapping(path="/remove/{id}")
    public ResponseEntity<SuccessResponseDTO> removeTrainingSheet(@PathVariable(required = true, name = "id") int id) throws SchedaAllenamentoException {
        SuccessResponseDTO success=service.removeSchedaAllenamento(id);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping(path="/idCliente/{id}")
    public ResponseEntity<List<SchedaAllenamentoViewDTO>> getAllTrainingSheetByIdCliente(@PathVariable(required = true, name = "id") int id) throws SchedaAllenamentoException {
        List<SchedaAllenamentoViewDTO> ListDto = service.findByIdCliente(id);
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/idPersonalTrainer/{id}")
    public ResponseEntity<List<SchedaAllenamentoViewDTO>> getAllTrainingSheetByIdPersonalTrainer(@PathVariable(required = true, name = "id") int id) throws SchedaAllenamentoException {
        List<SchedaAllenamentoViewDTO> ListDto = service.findByIdPersonalTrainer(id);
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }
    @GetMapping(path="/idScheda/{id}")
    public ResponseEntity<List<ContenutoSchedaAllenamentoViewDTO>> getAllTrainingSheetContentByIdScheda(@PathVariable(required = true, name = "id") int id) throws ContenutoSchedaAllenamentoException {
        List<ContenutoSchedaAllenamentoViewDTO> ListDto = contenutoSchedaAllenamentoService.findBySchedaAllenamento(id);
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

}
