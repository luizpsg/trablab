package com.advanced.comidinhasveganas.entities;

import java.util.ArrayList;
import java.util.List;

import com.advanced.comidinhasveganas.entities.enums.TipoItem;

import jakarta.persistence.Entity;

@Entity
public class MenuFechado extends Cardapio {

  private Double precoFixo;

  public MenuFechado() {
  }

  public MenuFechado(String nome, Restaurante restaurante, Double precoFixo) {
    super(nome, restaurante);
    this.precoFixo = precoFixo;
  }

  public Double getPrecoFixo() {
    return precoFixo;
  }

  public void setPrecoFixo(Double precoFixo) {
    this.precoFixo = precoFixo;
  }

  public List<ItemCardapio> getPratos() {
    List<ItemCardapio> pratos = new ArrayList<>();
    for (ItemCardapio item : getItens()) {
      if (item.getTipo() == TipoItem.COMIDA) {
        pratos.add(item);
      }
    }
    return pratos;
  }

  public List<ItemCardapio> getBebidas() {
    List<ItemCardapio> bebidas = new ArrayList<>();
    for (ItemCardapio item : getItens()) {
      if (item.getTipo() == TipoItem.BEBIDA) {
        bebidas.add(item);
      }
    }
    return bebidas;
  }

  @Override
  public String toString() {
    return "MenuFechado [precoFixo=" + precoFixo + ", toString()=" + super.toString() + "]";
  }
}
