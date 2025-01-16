package com.cybersecurity.progetto_cybersecurity.services;

import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.ClienteMapper;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.PrenotazioneMapper;
import com.cybersecurity.progetto_cybersecurity.entity.*;
import com.cybersecurity.progetto_cybersecurity.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PrenotazioneMapper prenotazioneMapper;

    @Autowired
    private VoloRepository voloRepository;

    @Autowired
    private PostoRepository postoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public List<Prenotazione> getPrenotazioniFromIdUtente(Long idUtente) {
        return prenotazioneRepository.getPrenotazioneByIdUtente(idUtente);
    }

    // Ottieni una prenotazione per ID
    @Transactional
    public List<PrenotazioneDTO> getPrenotazioneById(Long id) {
        List<Prenotazione> prenotazione = prenotazioneRepository.getPrenotazioniById(id);
        return prenotazione.stream().map(PrenotazioneMapper::prenotazioneToPrenotazioneDTO).toList();
    }

    // Crea una nuova prenotazione
    @Transactional
    public PrenotazioneDTO createPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        Volo volo = voloRepository.findById(prenotazioneDTO.getPrenotazioneId().getId_volo())
                .orElseThrow(() -> new RuntimeException("Volo non trovato"));
        Posto posto = postoRepository.findById(prenotazioneDTO.getPrenotazioneId().getId_posto())
                .orElseThrow(() -> new RuntimeException("Posto non trovato"));
        Cliente cliente = null;
        if (prenotazioneDTO.getPrenotazioneId().getId_cliente() != null) {
            cliente = clienteRepository.findById(prenotazioneDTO.getPrenotazioneId().getId_cliente())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        }

        Prenotazione prenotazione = prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO);
        prenotazione.setVolo(volo);
        prenotazione.setPosto(posto);
        prenotazione.setCliente(cliente);

        Prenotazione createdPrenotazione = prenotazioneRepository.save(prenotazione);
        return PrenotazioneMapper.prenotazioneToPrenotazioneDTO(createdPrenotazione);
    }


    @Transactional
    public PrenotazioneDTO savePrenotazione(PrenotazioneDTO prenotazione) {
        Prenotazione prenotazione1=prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazione);
        return PrenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazioneRepository.save(prenotazione1));
    }

    @Transactional
    public void saveAll(List<PrenotazioneDTO>prenotazioni){
        List<Prenotazione> entity=prenotazioni.stream().map(prenotazioneMapper::prenotazioneDTOToPrenotazione).toList();
        prenotazioneRepository.saveAll(entity);
    }

    @Transactional
    public List<ClienteDTO> getClienteByIdPrenotazione(Long idPrenotazione) {
        List<Cliente> clientiPrenotazione=prenotazioneRepository.getClientebyIdPrenotazione(idPrenotazione);
        List<ClienteDTO> clienteDTOS =new ArrayList<>();
        clientiPrenotazione.forEach(prenotazioneDTO->{clienteDTOS.add(ClienteMapper.toDTO(prenotazioneDTO));});
        return clienteDTOS;
    }

    public Long getMaxIdPrenotazione() {
        return prenotazioneRepository.getMaxId();
    }

    public Boolean isCheckin(Long idPrenotazione) {
        long numCheck=prenotazioneRepository.isCheckin(idPrenotazione);
        long numPass=prenotazioneRepository.countTotale(idPrenotazione);
        return numCheck == numPass;
    }
}
