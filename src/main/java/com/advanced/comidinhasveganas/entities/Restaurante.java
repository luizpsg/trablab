package com.advanced.comidinhasveganas.entities;

import java.time.LocalDateTime;
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

  @OneToMany(mappedBy = "restaurante")
  @JsonManagedReference
  private List<Requisicao> requisicoes = new ArrayList<>();

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

  public List<Requisicao> getRequisicoes() {
    return requisicoes;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public List<Cliente> setClientes(List<Cliente> clientes) {
    return this.clientes = clientes;
  }

  public List<Mesa> setMesas(List<Mesa> mesas) {
    return this.mesas = mesas;
  }

  public List<Requisicao> setRequisicoes(List<Requisicao> requisicoes) {
    return this.requisicoes = requisicoes;
  }

  public void addMesa(Mesa mesa) {
    mesas.add(mesa);
  }

  public void removeMesa(Mesa mesa) {
    mesas.remove(mesa);
  }

  public Cliente getClienteByTelefone(String telefone) {
    return clientes.stream().filter(c -> c.getTelefone().equals(telefone)).findFirst().orElse(null);
  }

  public void addCliente(Cliente cliente) {
    clientes.add(cliente);
  }

  public void removeCliente(Cliente cliente) {
    clientes.remove(cliente);
  }

  public void addRequisicao(Requisicao requisicao) {
    requisicoes.add(requisicao);
  }

  public void removeRequisicao(Requisicao requisicao) {
    requisicoes.remove(requisicao);
  }

  public void atualizarRequisicoes() {
    requisicoes.stream().filter(r -> !r.getIsAtendida()).forEach(
        req -> {
          mesas.stream().filter(m -> !m.getIsOcupada() && m.getLugares() >= req.getQuantidadePessoas()).findFirst()
              .ifPresent(
                  mesa -> {
                    req.setMesa(mesa);
                    req.setIsAtendida(true);
                    req.setDataHoraInicio(LocalDateTime.now());
                    mesa.ocupar();
                  });
        });
  }

  @Override
  public String toString() {
    return "Restaurante [id=" + id + ", nome=" + nome + ", endereco=" + endereco + "]";
  }

}
