package com.advanced.comidinhasveganas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advanced.comidinhasveganas.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
