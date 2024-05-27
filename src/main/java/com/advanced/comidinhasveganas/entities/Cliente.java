package com.advanced.comidinhasveganas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idCliente;
  private String nome;

  // Construtores, getters e setters
  public Cliente(Long idCliente, String nome) {
    this.idCliente = idCliente;
    this.nome = nome;
  }

  public Cliente() {
  }

  public Long getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public String toString() {
    return "Cliente [idCliente=" + idCliente + ", nome=" + nome + "]";
  }
}
