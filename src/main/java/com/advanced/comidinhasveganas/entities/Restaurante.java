package com.advanced.comidinhasveganas.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_restaurantes")
public class Restaurante {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  private String endereco;

  @OneToMany(mappedBy = "restaurante")
  @JsonManagedReference
  private List<Mesa> mesas = new ArrayList<>();

  @OneToMany(mappedBy = "restaurante")
  @JsonManagedReference
  private List<Cliente> clientes = new ArrayList<>();

  public Restaurante() {
  }

  public Restaurante(String nome, String endereco) {
    setNome(nome);
    setEndereco(endereco);
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEndereco() {
    return endereco;
  }

  public List<Mesa> getMesas() {
    return mesas;
  }

  public List<Cliente> getClientes() {
    return clientes;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public void addMesa(Mesa mesa) {
    mesas.add(mesa);
  }

  public void removeMesa(Mesa mesa) {
    mesas.remove(mesa);
  }

  public void addCliente(Cliente cliente) {
    clientes.add(cliente);
  }

  public void removeCliente(Cliente cliente) {
    clientes.remove(cliente);
  }

  @Override
  public String toString() {
    return "Restaurante [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", mesas=" + mesas + "]";
  }

}
