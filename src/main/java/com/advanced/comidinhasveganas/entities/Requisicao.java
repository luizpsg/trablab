package com.advanced.comidinhasveganas.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Requisicao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idRequisicao;
  private LocalDateTime dataHrEntrada;
  private LocalDateTime dataHrSaida;

  @ManyToOne
  private Cliente cliente;
  private int numeroPessoas;
  private boolean atendida;

  @ManyToOne
  private Mesa mesa;
  private double conta;

  @OneToMany
  private List<Pedido> pedidos;

  // Construtores, getters e setters
  public Requisicao(Long idRequisicao, LocalDateTime dataHrEntrada, LocalDateTime dataHrSaida, Cliente cliente,
      int numeroPessoas, boolean atendida, Mesa mesa, double conta, List<Pedido> pedidos) {
    this.idRequisicao = idRequisicao;
    this.dataHrEntrada = dataHrEntrada;
    this.dataHrSaida = dataHrSaida;
    this.cliente = cliente;
    this.numeroPessoas = numeroPessoas;
    this.atendida = atendida;
    this.mesa = mesa;
    this.conta = conta;
    this.pedidos = pedidos;
  }

  public Requisicao() {
  }

  public Long getIdRequisicao() {
    return idRequisicao;
  }

  public void setIdRequisicao(Long idRequisicao) {
    this.idRequisicao = idRequisicao;
  }

  public LocalDateTime getDataHrEntrada() {
    return dataHrEntrada;
  }

  public void setDataHrEntrada(LocalDateTime dataHrEntrada) {
    this.dataHrEntrada = dataHrEntrada;
  }

  public LocalDateTime getDataHrSaida() {
    return dataHrSaida;
  }

  public void setDataHrSaida(LocalDateTime dataHrSaida) {
    this.dataHrSaida = dataHrSaida;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public int getNumeroPessoas() {
    return numeroPessoas;
  }

  public void setNumeroPessoas(int numeroPessoas) {
    this.numeroPessoas = numeroPessoas;
  }

  public boolean isAtendida() {
    return atendida;
  }

  public void setAtendida(boolean atendida) {
    this.atendida = atendida;
  }

  public Mesa getMesa() {
    return mesa;
  }

  public void setMesa(Mesa mesa) {
    this.mesa = mesa;
  }

  public double getConta() {
    return conta;
  }

  public void setConta(double conta) {
    this.conta = conta;
  }

  public List<Pedido> getPedidos() {
    return pedidos;
  }

  public void setPedidos(List<Pedido> pedidos) {
    this.pedidos = pedidos;
  }

  public long calcularDuracao() {
    return dataHrSaida != null ? java.time.Duration.between(dataHrEntrada, dataHrSaida).toMinutes() : 0;
  }

  public void adicionaPedido(Pedido pedido) {
    pedidos.add(pedido);
  }

  public void encerraRequisicao() {
    this.atendida = false;
    this.dataHrSaida = LocalDateTime.now();
  }

  public boolean clienteSentado() {
    return this.atendida;
  }

  public void pagarConta() {
    this.conta = pedidos.stream()
        .mapToDouble(pedido -> pedido.getTotalConta())
        .sum();
  }

  public double dividirConta(int numeroPessoas) {
    return this.conta / numeroPessoas;
  }
}
