package com.advanced.comidinhasveganas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.ItemCardapio;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Requisicao;
import com.advanced.comidinhasveganas.entities.Restaurante;
import com.advanced.comidinhasveganas.entities.enums.TipoItem;

public class RestauranteTest {

  private Restaurante restaurante;
  private Mesa mesa1;
  private Mesa mesa2;
  private Cliente cliente1;
  private Cliente cliente2;
  private Requisicao requisicao1;
  private Requisicao requisicao2;
  private ItemCardapio itemCardapio1;
  private ItemCardapio itemCardapio2;

  @BeforeEach
  public void setUp() {
    restaurante = new Restaurante("Restaurante Test", "123 Main St");
    mesa1 = new Mesa(4);
    mesa2 = new Mesa(6);
    cliente1 = new Cliente("John Doe", "123456");
    cliente2 = new Cliente("Jane Smith", "654321");
    requisicao1 = new Requisicao(cliente1, 4);
    requisicao2 = new Requisicao(cliente2, 6);
    itemCardapio1 = new ItemCardapio("Vegan Burger", 10.99, TipoItem.COMIDA);
    itemCardapio2 = new ItemCardapio("Vegan Pizza", 12.99, TipoItem.COMIDA);

    restaurante.addMesa(mesa1);
    restaurante.addMesa(mesa2);
    restaurante.addCliente(cliente1);
    restaurante.addCliente(cliente2);
    restaurante.addRequisicao(requisicao1);
    restaurante.addRequisicao(requisicao2);
    restaurante.addItemCardapio(itemCardapio1);
    restaurante.addItemCardapio(itemCardapio2);
  }

  @Test
  public void testGetId() {
    Assertions.assertNull(restaurante.getId());
  }

  @Test
  public void testGetTaxaServico() {
    Assertions.assertEquals(1.1, restaurante.getTaxaServico());
  }

  @Test
  public void testGetNome() {
    Assertions.assertEquals("Restaurante Test", restaurante.getNome());
  }

  @Test
  public void testSetNome() {
    restaurante.setNome("New Name");
    Assertions.assertEquals("New Name", restaurante.getNome());
  }

  @Test
  public void testGetEndereco() {
    Assertions.assertEquals("123 Main St", restaurante.getEndereco());
  }

  @Test
  public void testSetEndereco() {
    restaurante.setEndereco("456 Elm St");
    Assertions.assertEquals("456 Elm St", restaurante.getEndereco());
  }

  @Test
  public void testGetMesas() {
    List<Mesa> expected = new ArrayList<>();
    expected.add(mesa1);
    expected.add(mesa2);
    Assertions.assertEquals(expected, restaurante.getMesas());
  }

  @Test
  public void testGetClientes() {
    List<Cliente> expected = new ArrayList<>();
    expected.add(cliente1);
    expected.add(cliente2);
    Assertions.assertEquals(expected, restaurante.getClientes());
  }

  @Test
  public void testGetRequisicoes() {
    List<Requisicao> expected = new ArrayList<>();
    expected.add(requisicao1);
    expected.add(requisicao2);
    Assertions.assertEquals(expected, restaurante.getRequisicoes());
  }

  @Test
  public void testGetItensCardapio() {
    List<ItemCardapio> expected = new ArrayList<>();
    expected.add(itemCardapio1);
    expected.add(itemCardapio2);
    Assertions.assertEquals(expected, restaurante.getItensCardapio());
  }

  @Test
  public void testAddItemCardapio() {
    ItemCardapio newItem = new ItemCardapio("Vegan Pasta", 9.99, TipoItem.COMIDA);
    restaurante.addItemCardapio(newItem);
    Assertions.assertTrue(restaurante.getItensCardapio().contains(newItem));
  }

  @Test
  public void testRemoveItemCardapio() {
    restaurante.removeItemCardapio(itemCardapio1);
    Assertions.assertFalse(restaurante.getItensCardapio().contains(itemCardapio1));
  }

  @Test
  public void testAddMesa() {
    Mesa newMesa = new Mesa(2);
    restaurante.addMesa(newMesa);
    Assertions.assertTrue(restaurante.getMesas().contains(newMesa));
  }

  @Test
  public void testRemoveMesa() {
    restaurante.removeMesa(mesa1);
    Assertions.assertFalse(restaurante.getMesas().contains(mesa1));
  }

  @Test
  public void testGetClienteByTelefone() {
    Optional<Cliente> optionalCliente = restaurante.getClienteByTelefone("123456");
    Assertions.assertTrue(optionalCliente.isPresent());
    Assertions.assertEquals(cliente1, optionalCliente.get());
  }

  @Test
  public void testAddCliente() {
    Cliente newCliente = new Cliente("Bob Smith", "987654");
    restaurante.addCliente(newCliente);
    Assertions.assertTrue(restaurante.getClientes().contains(newCliente));
  }

  @Test
  public void testRemoveCliente() {
    restaurante.removeCliente(cliente1);
    Assertions.assertFalse(restaurante.getClientes().contains(cliente1));
  }

  @Test
  public void testAddRequisicao() {
    Requisicao newRequisicao = new Requisicao(cliente1, 2);
    restaurante.addRequisicao(newRequisicao);
    Assertions.assertTrue(restaurante.getRequisicoes().contains(newRequisicao));
  }

  @Test
  public void testRemoveRequisicao() {
    restaurante.removeRequisicao(requisicao1);
    Assertions.assertFalse(restaurante.getRequisicoes().contains(requisicao1));
  }

  @Test
  public void testGetRequisicoesNaoAtendidas() {
    List<Requisicao> expected = new ArrayList<>();
    expected.add(requisicao1);
    expected.add(requisicao2);
    Assertions.assertEquals(expected, restaurante.getRequisicoesNaoAtendidas());
  }

  @Test
  public void testGetRequisicoesAtendidas() {
    List<Requisicao> expected = new ArrayList<>();
    Assertions.assertEquals(expected, restaurante.getRequisicoesAtendidas());
  }

  @Test
  public void testGetRequisicoesNaoFinalizadas() {
    List<Requisicao> expected = new ArrayList<>();
    expected.add(requisicao1);
    expected.add(requisicao2);
    Assertions.assertEquals(expected, restaurante.getRequisicoesNaoFinalizadas());
  }

  @Test
  public void testGetRequisicoesFinalizadas() {
    List<Requisicao> expected = new ArrayList<>();
    Assertions.assertEquals(expected, restaurante.getRequisicoesFinalizadas());
  }

  @Test
  public void testGetMesasDisponiveis() {
    List<Mesa> expected = new ArrayList<>();
    expected.add(mesa1);
    expected.add(mesa2);
    Assertions.assertEquals(expected, restaurante.getMesasDisponiveis());
  }

  @Test
  public void testGetMesasOcupadas() {
    List<Mesa> expected = new ArrayList<>();
    Assertions.assertEquals(expected, restaurante.getMesasOcupadas());
  }

  @Test
  public void testAtualizarRequisicoes() {
    restaurante.atualizarRequisicoes();
    Assertions.assertTrue(requisicao1.getIsAtendida());
    Assertions.assertTrue(requisicao2.getIsAtendida());
    Assertions.assertEquals(mesa1, requisicao1.getMesa());
    Assertions.assertEquals(mesa2, requisicao2.getMesa());
  }

  @Test
  public void testToString() {
    String expected = "Restaurante [id=null, nome=Restaurante Test, endereco=123 Main St]";
    Assertions.assertEquals(expected, restaurante.toString());
  }
}