package com.luizpsg.advanced.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizpsg.advanced.entities.Requisicao;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {

}
