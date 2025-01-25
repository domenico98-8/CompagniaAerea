package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.security.InputValidator;
import com.cybersecurity.progetto_cybersecurity.services.PostoService;
import com.cybersecurity.progetto_cybersecurity.utility.VoloPostoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://localhost", allowCredentials = "true")
@RestController
@RequestMapping("/api/posti")
public class PostoController {

    @Autowired
    private PostoService postoService;

    @Autowired
    private InputValidator validator;

    @GetMapping
    public List<PostoDTO> getAllPosti() {
        return postoService.getAllPosti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostoDTO> getPostoById(@PathVariable Long id) {
        if(!validator.isValidId(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PostoDTO postoDTO = postoService.getPostoById(id);
        return ResponseEntity.ok(postoDTO);
    }

    @GetMapping("/findByCodiceVolo/{codiceVolo}")
    public ResponseEntity<List<VoloPostoResponse>> getPostoByVolo(@PathVariable String codiceVolo)
    {
        if(!validator.isValidString(codiceVolo)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(postoService.getPostiByCodiceVolo(codiceVolo));
    }

    @PostMapping
    public ResponseEntity<PostoDTO> createPosto(@RequestBody PostoDTO postoDTO) {
        PostoDTO createdPostoDTO = postoService.createPosto(postoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostoDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosto(@PathVariable Long id) {
        if(!validator.isValidId(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        postoService.deletePosto(id);
        return ResponseEntity.noContent().build();  // Restituisce HTTP 204 (No Content)
    }


}

