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
    public PrenotazioneDTO getPrenotazioneById(Long id) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
        return PrenotazioneMapper.prenotazioneToPrenotazioneDTO(prenotazione);
    }

    // Crea una nuova prenotazione
    @Transactional
    public PrenotazioneDTO createPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        Volo volo = voloRepository.findById(prenotazioneDTO.getIdVolo())
                .orElseThrow(() -> new RuntimeException("Volo non trovato"));
        Posto posto = postoRepository.findById(prenotazioneDTO.getIdPosto())
                .orElseThrow(() -> new RuntimeException("Posto non trovato"));
        Cliente cliente = null;
        if (prenotazioneDTO.getIdCliente() != null) {
            cliente = clienteRepository.findById(prenotazioneDTO.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        }

        Prenotazione prenotazione = prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO);
        prenotazione.setVolo(volo);
        prenotazione.setPosto(posto);
        prenotazione.setCliente(cliente);

        Prenotazione createdPrenotazione = prenotazioneRepository.save(prenotazione);
        return PrenotazioneMapper.prenotazioneToPrenotazioneDTO(createdPrenotazione);
    }

    // Aggiorna una prenotazione esistente
    @Transactional
    public PrenotazioneDTO updatePrenotazione(Long id, PrenotazioneDTO prenotazioneDTO) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new RuntimeException("Prenotazione non trovata");
        }
        Prenotazione prenotazione = prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazioneDTO);
        prenotazione.setId(id);
        Prenotazione updatedPrenotazione = prenotazioneRepository.save(prenotazione);
        return PrenotazioneMapper.prenotazioneToPrenotazioneDTO(updatedPrenotazione);
    }

    // Elimina una prenotazione
    @Transactional
    public void deletePrenotazione(Long id) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new RuntimeException("Prenotazione non trovata");
        }
        prenotazioneRepository.deleteById(id);
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
}
