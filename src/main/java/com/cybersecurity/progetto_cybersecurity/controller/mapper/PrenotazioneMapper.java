package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.entity.*;
import com.cybersecurity.progetto_cybersecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PrenotazioneMapper {

    @Autowired
    private PostoRepository postoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VoloRepository voloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private BagaglioRepository bagaglioRepository;

    public static PrenotazioneDTO prenotazioneToPrenotazioneDTO(Prenotazione prenotazione) {
        if (prenotazione == null) {
            return null;
        }
        return PrenotazioneDTO.builder()
                .prenotazioneId(prenotazione.getId())
                .idBagaglio(prenotazione.getBagaglio() != null ? prenotazione.getBagaglio().getId() : null)
                .dataPrenotazione(prenotazione.getDataPrenotazione())
                .costo(prenotazione.getCosto())
                .idUtente(prenotazione.getUtente().getId())
                .checkin(prenotazione.isCheckin())
                .build();
    }

    public Prenotazione prenotazioneDTOToPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        if (prenotazioneDTO == null) {
            return null;
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(prenotazioneDTO.getPrenotazioneId());
        Optional<Posto> posto =postoRepository.findById(prenotazioneDTO.getPrenotazioneId().getId_posto());
        prenotazione.setPosto(posto.orElse(null));
        Optional<Cliente> cliente=clienteRepository.findById(prenotazioneDTO.getPrenotazioneId().getId_cliente());
        prenotazione.setCliente(cliente.orElse(null));
        Optional<Volo> volo=voloRepository.findById(prenotazioneDTO.getPrenotazioneId().getId_volo());
        prenotazione.setVolo(volo.orElse(null));
        Optional<Utente> utente=utenteRepository.findById(prenotazioneDTO.getIdUtente());
        prenotazione.setUtente(utente.orElse(null));
        if(prenotazioneDTO.getIdBagaglio()!=null){
            Optional<Bagaglio> bagaglio=bagaglioRepository.findById(prenotazioneDTO.getIdBagaglio());
            prenotazione.setBagaglio(bagaglio.orElse(null));
            prenotazione.setIdBagaglio(prenotazioneDTO.getIdBagaglio());
        }
        prenotazione.setCosto(prenotazioneDTO.getCosto());
        prenotazione.setDataPrenotazione(LocalDateTime.now());
        prenotazione.setCheckin(prenotazioneDTO.isCheckin());

        return prenotazione;
    }
}
