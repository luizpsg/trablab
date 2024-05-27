package com.advanced.comidinhasveganas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mesa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idMesa;
  private int capacidade;
  private int clientesSentados;

  // Getters e setters

  public Mesa(Long idMesa, int capacidade, int clientesSentados) {
    this.idMesa = idMesa;
    this.capacidade = capacidade;
    this.clientesSentados = clientesSentados;
  }

  public Mesa() {
  }

  public Long getIdMesa() {
    return idMesa;
  }

  public void setIdMesa(Long idMesa) {
    this.idMesa = idMesa;
  }

  public int getCapacidade() {
    return capacidade;
  }

  public void setCapacidade(int capacidade) {
    this.capacidade = capacidade;
  }

  public int getClientesSentados() {
    return clientesSentados;
  }

  public void setClientesSentados(int clientesSentados) {
    this.clientesSentados = clientesSentados;
  }

  public boolean verificarDisponibilidade() {
    return clientesSentados < capacidade;
  }

  @Override
  public String toString() {
    return "Mesa [idMesa=" + idMesa + ", capacidade=" + capacidade + ", clientesSentados=" + clientesSentados + "]";
  }

}
