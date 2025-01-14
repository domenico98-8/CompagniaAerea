package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.controller.dto.BagaglioDTO;
import com.cybersecurity.progetto_cybersecurity.services.BagaglioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bagaglio")
public class BagaglioController {
    @Autowired
    private BagaglioService bagaglioService;

    @GetMapping("/getAllBagagli")
    public ResponseEntity<List<BagaglioDTO>> getAllBagagli() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bagaglioService.findAll());
    }
}
