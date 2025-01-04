package com.cybersecurity.progetto_cybersecurity.repository;

import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Cliente findByDocumento(String documento);

    // Altri metodi personalizzati, se necessari
}

