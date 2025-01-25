package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.jwt.JwtUtil;
import com.cybersecurity.progetto_cybersecurity.security.InputValidator;
import com.cybersecurity.progetto_cybersecurity.security.PasswordService;
import com.cybersecurity.progetto_cybersecurity.services.ClienteService;
import com.cybersecurity.progetto_cybersecurity.services.UtenteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.cybersecurity.progetto_cybersecurity.utility.Utils.createResponse;

@CrossOrigin(origins = "https://localhost", allowCredentials = "true")
@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final JwtUtil jwtUtil;

    public UtenteController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private InputValidator validator;

    @PostMapping("/registrazione")
    public ResponseEntity<String> registraUtente(@RequestBody UtenteDTO utente) {
        if(utenteService.existUtente(utente.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Utente già registrato con questa email.");
        }

        utente.setPassword(passwordService.hashPassword(utente.getPassword()));

        UtenteDTO userSaved = utenteService.saveUtente(utente);
        ClienteDTO clienteDTO=clienteService.getClienteByDocumento(utente.getDocumento());
        if(clienteDTO==null){
            clienteDTO=new ClienteDTO();
            clienteDTO.setNome(utente.getNome());
            clienteDTO.setCognome(utente.getCognome());
            clienteDTO.setDocumento(utente.getDocumento());
            clienteDTO.setDataNascita(utente.getDataNascita());
            clienteDTO.setIdUtente(userSaved.getId());
            clienteDTO.setSesso(utente.getSesso());
        }else{
            if(clienteDTO.getIdUtente()==null) {
                clienteDTO.setIdUtente(userSaved.getId());
            }else{
                utenteService.deleteUtente(userSaved.getEmail());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utente già registrato con il documento indicato.");
            }
        }
        clienteService.saveCliente(clienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Utente Creato");
    }

    //Restiruire un token
    @PostMapping("/login")
    public ResponseEntity<Object> loginUtente(@RequestBody String credenziali, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        String email = "";
        String password = "";
        try {
            // Converti la stringa JSON in un oggetto JsonNode
            JsonNode jsonNode = objectMapper.readTree(credenziali);

            // Estrai i campi dal JSON
            email = jsonNode.get("email").asText();
            password = jsonNode.get("password").asText();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse("Richiesta errata!",HttpStatus.BAD_REQUEST.value()));
        }

        if(!validator.isValid(email)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createResponse("Email non valida!",HttpStatus.UNAUTHORIZED.value()));
        }

        Optional<UtenteDTO> userDto = utenteService.getUtente(email);
        if(userDto.isPresent()){
            password = passwordService.hashPassword(password);
            UtenteDTO utenteDTO = userDto.get();
            if(password.equals(utenteDTO.getPassword())){
                // Genera un token
                String token = jwtUtil.generateToken(password);

                // Creazione del cookie HttpOnly con il token
                ResponseCookie tokenJwt = ResponseCookie.from("token", token)
                        .httpOnly(true)
                        .secure(true) // Usa true per HTTPS
                        .path("/")
                        .sameSite("Strict") // o "Lax", dipende dal tuo caso d'uso
                        .maxAge(jwtUtil.getJwtExpiration(token)) // Durata del cookie in secondi
                        .build();

                // Aggiungi il cookie per l'ID utente
                ResponseCookie userIdCookie = ResponseCookie.from("userId", utenteDTO.getId().toString())
                        .httpOnly(true)  // Non è necessario HttpOnly per l'ID
                        .secure(true) // Usa true per HTTPS
                        .path("/")
                        .sameSite("Strict")
                        .maxAge(jwtUtil.getJwtExpiration(token)) // Durata del cookie in secondi
                        .build();

                // Aggiungi entrambi i cookie alla risposta
                response.addHeader("Set-Cookie", tokenJwt.toString());
                response.addHeader("Set-Cookie", userIdCookie.toString());
                // Ritorna una risposta con il successivo id dell'utente
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(createResponse("Login effettuato con successo", HttpStatus.ACCEPTED.value()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createResponse("Credenziali non valide",HttpStatus.UNAUTHORIZED.value()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createResponse("Utente non trovato!",HttpStatus.UNAUTHORIZED.value()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logoutUtente(HttpServletResponse response) {
        // Imposta il cookie per il token JWT con Expires a una data passata
        ResponseCookie tokenCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true) // Usa true per HTTPS
                .path("/")    // Lo stesso path del cookie originale
                .sameSite("Strict")
                .maxAge(0)    // Scadenza immediata
                .build();

        // Imposta il cookie per l'ID utente con Expires a una data passata
        ResponseCookie userIdCookie = ResponseCookie.from("userId", "")
                .httpOnly(true)  // Non necessario HttpOnly per l'ID
                .secure(true)     // Usa true per HTTPS
                .path("/")
                .sameSite("Strict")
                .maxAge(0)        // Scadenza immediata
                .build();

        // Aggiungi entrambi i cookie alla risposta
        response.addHeader("Set-Cookie", tokenCookie.toString());
        response.addHeader("Set-Cookie", userIdCookie.toString());

        // Ritorna una risposta di successo che segnala che l'utente è stato disconnesso
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createResponse("Logout effettuato con successo", HttpStatus.ACCEPTED.value()));
    }

    @GetMapping("/getUserAccount/{codiceUtente}")
    public ResponseEntity<ClienteDTO> getUserAccount(@PathVariable String codiceUtente) {
        if(!validator.isValidString(codiceUtente)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ClienteDTO cliente=clienteService.findClienteByUtenteId(Long.parseLong(codiceUtente));
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }
}