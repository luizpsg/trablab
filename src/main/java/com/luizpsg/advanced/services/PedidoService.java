package com.luizpsg.advanced.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizpsg.advanced.entities.Pedido;
import com.luizpsg.advanced.repositories.PedidoRepository;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository repository;

  public List<Pedido> findAll() {
    return repository.findAll();
  }

  public Pedido findById(Long id) {
    Optional<Pedido> obj = repository.findById(id);
    return obj.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
  }

  @Transactional
  public Pedido insert(Pedido pedido) {
    return repository.save(pedido);
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
