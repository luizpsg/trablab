package com.advanced.comidinhasveganas.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Requisicao requisicao;

  @ManyToMany
  private List<ItemCardapio> itemCardapio;
  private int quantidade;
  private double totalConta;
  private double dividirConta;

  // Construtores, getters e setters
  public Pedido(Long id, Requisicao requisicao, List<ItemCardapio> itemCardapio, int quantidade, double totalConta,
      double dividirConta) {
    this.id = id;
    this.requisicao = requisicao;
    this.itemCardapio = itemCardapio;
    this.quantidade = quantidade;
    this.totalConta = totalConta;
    this.dividirConta = dividirConta;
  }

  public Pedido() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Requisicao getRequisicao() {
    return requisicao;
  }

  public void setRequisicao(Requisicao requisicao) {
    this.requisicao = requisicao;
  }

  public List<ItemCardapio> getItemCardapio() {
    return itemCardapio;
  }

  public void setItemCardapio(List<ItemCardapio> itemCardapio) {
    this.itemCardapio = itemCardapio;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public double getTotalConta() {
    return totalConta;
  }

  public void setTotalConta(double totalConta) {
    this.totalConta = totalConta;
  }

  public double getDividirConta() {
    return dividirConta;
  }

  public void setDividirConta(double dividirConta) {
    this.dividirConta = dividirConta;
  }

  public void addItem(ItemCardapio item) {
    itemCardapio.add(item);
  }

  public double calcularConta() {
    this.totalConta = itemCardapio.stream()
        .mapToDouble(ItemCardapio::getPreco)
        .sum();
    this.totalConta += this.totalConta * 0.10; // Taxa de serviço de 10%
    return this.totalConta;
  }

  public double dividirConta(int numeroPessoas) {
    this.dividirConta = this.totalConta / numeroPessoas;
    return this.dividirConta;
  }
}
