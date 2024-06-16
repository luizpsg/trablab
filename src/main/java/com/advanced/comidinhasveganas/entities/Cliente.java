package com.advanced.comidinhasveganas.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clientes")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  private String telefone;

  @ManyToOne
  @JoinColumn(name = "restaurante_id")
  @JsonBackReference
  private Restaurante restaurante;

  public Cliente() {
  }

  public Cliente(String nome, String telefone) {
    setNome(nome);
    setTelefone(telefone);
  }

  public Cliente(String nome, String telefone, Restaurante restaurante) {
    setNome(nome);
    setTelefone(telefone);
    setRestaurante(restaurante);
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public Restaurante getRestaurante() {
    return restaurante;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public void setRestaurante(Restaurante restaurante) {
    this.restaurante = restaurante;
  }

  @Override
  public String toString() {
    return "Cliente [id=" + id + ", nome=" + nome + ", telefone=" + telefone + "]";
  }

}
