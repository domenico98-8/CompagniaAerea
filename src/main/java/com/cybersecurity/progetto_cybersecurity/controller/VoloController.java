package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.services.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/voli")
public class VoloController {

    @Autowired
    private VoloService voloService;

    @GetMapping
    public List<Volo> trovaTutti() {
        return voloService.trovaTutti();
    }
}