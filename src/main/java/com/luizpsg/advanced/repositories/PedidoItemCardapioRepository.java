package com.luizpsg.advanced.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizpsg.advanced.entities.PedidoItemCardapio;
import com.luizpsg.advanced.entities.pk.PedidoItemCardapioPK;

public interface PedidoItemCardapioRepository extends JpaRepository<PedidoItemCardapio, PedidoItemCardapioPK> {

}
