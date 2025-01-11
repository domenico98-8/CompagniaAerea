package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPosto;
import com.cybersecurity.progetto_cybersecurity.services.PostoService;
import com.cybersecurity.progetto_cybersecurity.utility.VoloPostoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/posti")
public class PostoController {

    @Autowired
    private PostoService postoService;

    // GET: Ottieni tutti i posti
    @GetMapping
    public List<PostoDTO> getAllPosti() {
        return postoService.getAllPosti();
    }

    // GET: Ottieni un posto specifico per ID
    @GetMapping("/{id}")
    public ResponseEntity<PostoDTO> getPostoById(@PathVariable Long id) {
        PostoDTO postoDTO = postoService.getPostoById(id);
        return ResponseEntity.ok(postoDTO);
    }

    // GET: Ottieni un posto specifico in base al Codice Volo
    @GetMapping("/findByCodiceVolo/{codiceVolo}")
    public ResponseEntity<List<VoloPostoResponse>> getPostoByVolo(@PathVariable String codiceVolo) {
        return ResponseEntity.ok(postoService.getPostiByCodiceVolo(codiceVolo));

    }

    // POST: Crea un nuovo posto
    @PostMapping
    public ResponseEntity<PostoDTO> createPosto(@RequestBody PostoDTO postoDTO) {
        PostoDTO createdPostoDTO = postoService.createPosto(postoDTO);
        return ResponseEntity.status(201).body(createdPostoDTO);
    }


    // DELETE: Elimina un posto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosto(@PathVariable Long id) {
        postoService.deletePosto(id);
        return ResponseEntity.noContent().build();  // Restituisce HTTP 204 (No Content)
    }


}

