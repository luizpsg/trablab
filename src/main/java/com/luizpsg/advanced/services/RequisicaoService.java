package com.luizpsg.advanced.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizpsg.advanced.entities.Requisicao;
import com.luizpsg.advanced.repositories.RequisicaoRepository;

@Service
public class RequisicaoService {

  @Autowired
  private RequisicaoRepository repository;

  public List<Requisicao> findAll() {
    return repository.findAll();
  }

  public Optional<Requisicao> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Requisicao insert(Requisicao requisicao) {
    return repository.save(requisicao);
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Transactional
  public Requisicao update(Long id, Requisicao requisicao) {
    Requisicao entity = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
    updateData(entity, requisicao);
    return repository.save(entity);
  }

  private void updateData(Requisicao entity, Requisicao requisicao) {
    entity.setAtendida(requisicao.isAtendida());
    entity.setFinalizada(requisicao.isFinalizada());
    entity.setMesa(requisicao.getMesa());
    entity.setQuantidadePessoas(requisicao.getQuantidadePessoas());
    entity.setCliente(requisicao.getCliente());
    entity.setDataHoraInicio(requisicao.getDataHoraInicio());
    entity.setDataHoraFim(requisicao.getDataHoraFim());
    entity.setPedidos(requisicao.getPedidos());
    entity.setTotalConta(requisicao.getTotalConta());
    entity.setTotalPorPessoa(requisicao.getTotalPorPessoa());
  }

  public Optional<Requisicao> findRequisicaoByMesaId(Long mesaId) {
    return repository.findAll().stream()
        .filter(r -> r.getMesa().getId().equals(mesaId) && !r.isFinalizada())
        .findFirst();
  }
}