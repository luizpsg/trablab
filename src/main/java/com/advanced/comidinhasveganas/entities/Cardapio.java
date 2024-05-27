package com.advanced.comidinhasveganas.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cardapio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL)
  private List<ItemCardapio> itens;

  public Cardapio() {
    this.itens = new ArrayList<>();
  }

  public Cardapio(List<ItemCardapio> itens) {
    this.itens = itens;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<ItemCardapio> getItens() {
    return itens;
  }

  public void setItens(List<ItemCardapio> itens) {
    this.itens = itens;
  }

  public void addItem(ItemCardapio item) {
    this.itens.add(item);
  }
}
