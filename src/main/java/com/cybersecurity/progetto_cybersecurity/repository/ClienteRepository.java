package com.cybersecurity.progetto_cybersecurity.repository;

import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    @Override
    Optional<Cliente> findById(Long aLong);

    Optional<Cliente> findByDocumento(String documento);


    // Altri metodi personalizzati, se necessari
}

