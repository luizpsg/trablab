package com.luizpsg.advanced.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizpsg.advanced.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
