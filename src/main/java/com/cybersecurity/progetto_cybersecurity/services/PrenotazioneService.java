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
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PrenotazioneMapper prenotazioneMapper;

    @Autowired
    private VoloPostoRepository voloPostoRepository;

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

    public List<ClienteDTO> getClienteByIdPrenotazione(Long idPrenotazione) {
        List<Cliente> clientiPrenotazione=prenotazioneRepository.getClientebyIdPrenotazione(idPrenotazione);
        List<ClienteDTO> clienteDTOS =new ArrayList<>();
        clientiPrenotazione.forEach(prenotazioneDTO->{clienteDTOS.add(ClienteMapper.toDTO(prenotazioneDTO));});
        return clienteDTOS;
    }

    public Long getMaxIdPrenotazione() {
        return prenotazioneRepository.getMaxId();
    }

    @Transactional
    public void deletePrenotazione(Long idPrenotazione) {
        List<Prenotazione> p=prenotazioneRepository.getPrenotazioniById(idPrenotazione);
        p.forEach(g->{
            Optional<VoloPosto> optPosto=voloPostoRepository.findById(new VoloPostoId(g.getVolo().getId(),g.getPosto().getId()));
           if(optPosto.isPresent()){
               VoloPosto posto=optPosto.get();
               posto.setStato(false);
               voloPostoRepository.save(posto);
           }
        });
        prenotazioneRepository.deleteAll(p);
    }

    public Boolean getPrenotazioneByVoloCliente(Long idCliente,Long codiceVolo){
        Optional<Prenotazione> prenotazione=prenotazioneRepository.getPrenotazioneByVoloCliente(idCliente,codiceVolo);
        return prenotazione.isPresent();
    }
}
