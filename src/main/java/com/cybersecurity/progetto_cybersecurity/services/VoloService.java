package com.cybersecurity.progetto_cybersecurity.services;


import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoloService {

    @Autowired
    private VoloRepository voloRepository;

    public List<Volo> trovaTutti() {
        return voloRepository.findAll();
    }
}