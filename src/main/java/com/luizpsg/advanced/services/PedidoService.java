package com.luizpsg.advanced.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizpsg.advanced.entities.Pedido;
import com.luizpsg.advanced.entities.Requisicao;
import com.luizpsg.advanced.repositories.PedidoRepository;
import com.luizpsg.advanced.repositories.RequisicaoRepository;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository repository;

  @Autowired
  private RequisicaoRepository requisicaoRepository;

  public List<Pedido> findAll() {
    return repository.findAll();
  }

  public Optional<Pedido> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Pedido insert(Pedido pedido, Long idRequisicao) {
    Requisicao requisicao = requisicaoRepository.findById(idRequisicao)
        .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
    pedido.setRequisicao(requisicao);
    return repository.save(pedido);
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
