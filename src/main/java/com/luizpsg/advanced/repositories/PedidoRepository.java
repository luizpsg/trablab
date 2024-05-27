package com.luizpsg.advanced.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizpsg.advanced.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
