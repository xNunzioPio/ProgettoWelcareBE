package com.wellcare.controller;


import com.wellcare.controller.dto.cibo.CiboViewDTO;
import com.wellcare.exceptions.CiboException;
import com.wellcare.service.schedaAlimentare.CiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

    @Controller
    @RequestMapping(path = "private/cibo")
    public class CiboController {

        @Autowired
        public CiboService service;

        @GetMapping(path="/all")
        public ResponseEntity<List<CiboViewDTO>> getAllFood() throws CiboException {
            List<CiboViewDTO> ListDto = service.getAll();
            return new ResponseEntity<>(ListDto, HttpStatus.OK);
        }

        @GetMapping(path="/{id}")
        public ResponseEntity<CiboViewDTO> getSpecificFood(@PathVariable(required = true, name = "id") int id) throws CiboException {
            CiboViewDTO viewDto = service.getById(id);
            return new ResponseEntity<>(viewDto, HttpStatus.OK);
        }
    }

