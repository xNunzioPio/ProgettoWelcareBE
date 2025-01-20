package com.wellcare.controller;

import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAlimentareException;
import com.wellcare.exceptions.SchedaAlimentareException;
import com.wellcare.exceptions.SchedaAllenamentoException;
import com.wellcare.service.schedaAlimentare.CiboService;
import com.wellcare.service.schedaAlimentare.ContenutoSchedaAlimentareService;
import com.wellcare.service.schedaAlimentare.SchedaAlimentareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "private/schedaAlimentare")
public class SchedaAlimentareController {

    @Autowired
    public SchedaAlimentareService service;
    @Autowired
    public ContenutoSchedaAlimentareService contenutoSchedaAlimentareService;
    @Autowired
    public CiboService ciboService;

    @GetMapping(path="/all")
    public ResponseEntity<List<SchedaAlimentareViewDTO>> getAllFoodSheet() throws SchedaAlimentareException {
        List<SchedaAlimentareViewDTO> ListDto = service.getAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<SchedaAlimentareViewDTO> getSpecificFoodSheet(@PathVariable(required = true, name = "id") int id) throws SchedaAlimentareException {
        SchedaAlimentareViewDTO viewDto = service.getById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/create")
    public ResponseEntity<SchedaAlimentareViewDTO> createFoodSheet(@RequestBody SchedaAlimentareCreationDTO scheda) throws SchedaAlimentareException {
        SchedaAlimentareViewDTO viewDTO=service.createSchedaAlimentare(scheda);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @DeleteMapping(path="/remove/{id}")
    public ResponseEntity<SuccessResponseDTO> removeFoodSheet(@PathVariable(required = true, name = "id") int id)  {
        service.removeSchedaAlimentare(id);
        return new ResponseEntity<>(new SuccessResponseDTO("Eliminazione con successo!"), HttpStatus.OK);
    }

    @GetMapping(path="/idCliente/{id}")
    public ResponseEntity<List<SchedaAlimentareViewDTO>> getAllFoodSheetByIdCliente(@PathVariable(required = true, name = "id") int id) throws SchedaAlimentareException {
        List<SchedaAlimentareViewDTO> ListDto = service.findByIdCliente(id);
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/idNutrizionista/{id}")
    public ResponseEntity<List<SchedaAlimentareViewDTO>> getAllFoodSheetByIdNutrizionista(@PathVariable(required = true, name = "id") int id) throws SchedaAlimentareException {
        List<SchedaAlimentareViewDTO> ListDto = service.findByIdNutrizionista(id);
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }
    @GetMapping(path="/idScheda/{id}")
    public ResponseEntity<List<ContenutoSchedaAlimentareViewDTO>> getAllFoodSheetContentByIdScheda(@PathVariable(required = true, name = "id") int id) throws ContenutoSchedaAlimentareException {
        List<ContenutoSchedaAlimentareViewDTO> ListDto = contenutoSchedaAlimentareService.findBySchedaAlimentare(id);
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

}
