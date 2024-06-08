package com.luizpsg.advanced.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizpsg.advanced.entities.Pedido;
import com.luizpsg.advanced.entities.ItemCardapio;
import com.luizpsg.advanced.entities.Requisicao;
import com.luizpsg.advanced.repositories.PedidoRepository;
import com.luizpsg.advanced.repositories.ItemCardapioRepository;
import com.luizpsg.advanced.repositories.RequisicaoRepository;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository repository;

  @Autowired
  private ItemCardapioRepository itemCardapioRepository;

  @Autowired
  private RequisicaoRepository requisicaoRepository;

  public List<Pedido> findAll() {
    return repository.findAll();
  }

  public Optional<Pedido> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Pedido insert(Pedido pedido) {
    return repository.save(pedido);
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public void adicionarItemAoPedido(Long idRequisicao, Long idItemCardapio, int quantidade) {
    Requisicao req = requisicaoRepository.findById(idRequisicao)
        .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
    ItemCardapio item = itemCardapioRepository.findById(idItemCardapio)
        .orElseThrow(() -> new RuntimeException("Item do cardápio não encontrado"));

    Pedido pedido = new Pedido(null, item, quantidade);
    req.addPedido(pedido);
    atualizarTotalRequisicao(req);
    requisicaoRepository.save(req);
  }

  private void atualizarTotalRequisicao(Requisicao req) {
    double total = req.getPedidos().stream()
        .mapToDouble(p -> p.getItem().getPreco() * p.getQuantidade())
        .sum();
    total += total * 0.10; // Adiciona a taxa de serviço de 10%
    req.setTotalConta(total);
    req.setTotalPorPessoa(total / req.getQuantidadePessoas());
  }
}
