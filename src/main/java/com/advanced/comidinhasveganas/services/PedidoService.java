package com.advanced.comidinhasveganas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advanced.comidinhasveganas.entities.Pedido;
import com.advanced.comidinhasveganas.repositories.PedidoRepository;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  public List<Pedido> findAll() {
    return pedidoRepository.findAll();
  }

  public Optional<Pedido> findById(Long id) {
    return pedidoRepository.findById(id);
  }

  @Transactional
  public Pedido insert(Pedido pedido) {
    return pedidoRepository.save(pedido);
  }

  @Transactional
  public void deleteAll() {
    pedidoRepository.deleteAll();
  }

  @Transactional
  public void deleteById(Long id) {
    pedidoRepository.deleteById(id);
  }
}
