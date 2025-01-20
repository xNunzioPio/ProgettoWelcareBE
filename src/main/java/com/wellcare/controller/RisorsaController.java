package com.wellcare.controller;


import com.wellcare.controller.dto.risorsa.RisorsaCreationDTO;
import com.wellcare.controller.dto.risorsa.RisorsaViewDTO;
import com.wellcare.exceptions.RisorsaException;
import com.wellcare.service.risorsa.RisorsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path ="private/risorsa")
public class RisorsaController {

    @Autowired
    public RisorsaService service;

    @GetMapping(path="/all")
    public ResponseEntity<List<RisorsaViewDTO>> getAllResource() throws RisorsaException {
        List<RisorsaViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<RisorsaViewDTO> getSpecificResource(@PathVariable(required = true, name = "id") int id) throws RisorsaException {
        RisorsaViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/create")
    public ResponseEntity<RisorsaViewDTO> createResource(@RequestBody RisorsaCreationDTO ris)  {
        RisorsaViewDTO viewDTO=service.createRisorsa(ris);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @DeleteMapping(path="/remove/{id}")
    public ResponseEntity<String> removeResource(@PathVariable(required = true, name = "id") int id)  {
        service.removeRisorsa(id);
        return new ResponseEntity<>("Eliminazione con successo!", HttpStatus.OK);
    }

}
