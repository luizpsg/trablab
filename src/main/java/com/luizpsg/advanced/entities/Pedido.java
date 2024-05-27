package com.luizpsg.advanced.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "requisicao_id")
  private Requisicao requisicao;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private ItemCardapio item;

  private Integer quantidade;

  public Pedido() {
  }

  public Pedido(Long id, ItemCardapio item, Integer quantidade) {
    this.id = id;
    this.item = item;
    this.quantidade = quantidade;
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

  public ItemCardapio getItem() {
    return item;
  }

  public void setItem(ItemCardapio item) {
    this.item = item;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  @Override
  public String toString() {
    return "Pedido [id=" + id + ", item=" + item + ", quantidade=" + quantidade + "]";
  }
}
