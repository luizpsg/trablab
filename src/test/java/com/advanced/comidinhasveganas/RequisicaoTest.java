package com.advanced.comidinhasveganas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.ItemCardapio;
import com.advanced.comidinhasveganas.entities.ItemPedido;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Pedido;
import com.advanced.comidinhasveganas.entities.Requisicao;
import com.advanced.comidinhasveganas.entities.enums.TipoItem;

public class RequisicaoTest {

  private Requisicao requisicao;
  private Cliente cliente;
  private Mesa mesa;
  private Pedido pedido1;
  private ItemCardapio itemCardapio;
  private ItemPedido itemPedido;

  @BeforeEach
  public void setUp() {
    cliente = new Cliente("John Doe", "123456");
    mesa = new Mesa(6);
    requisicao = new Requisicao(cliente, 4);
    requisicao.setMesa(mesa);
    requisicao.setAtendida();
    requisicao.setPedidos(new ArrayList<>());
    pedido1 = new Pedido("normal");
    itemCardapio = new ItemCardapio("Vegan Burger", 10.99, TipoItem.COMIDA);
    itemPedido = new ItemPedido(itemCardapio, 2);
    pedido1.addItem(itemPedido);
    requisicao.addPedido(pedido1);
  }

  // @Test
  // public void testGetId() {
  // Assertions.assertNull(requisicao.getId());
  // }

  @Test
  public void testGetCliente() {
    Assertions.assertEquals(cliente, requisicao.getCliente());
  }

  @Test
  public void testSetCliente() {
    Cliente newCliente = new Cliente("Jane Smith", "654321");
    requisicao.setCliente(newCliente);
    Assertions.assertEquals(newCliente, requisicao.getCliente());
  }

  @Test
  public void testGetQuantidadePessoas() {
    Assertions.assertEquals(4, requisicao.getQuantidadePessoas());
  }

  @Test
  public void testSetQuantidadePessoas() {
    requisicao.setQuantidadePessoas(6);
    Assertions.assertEquals(6, requisicao.getQuantidadePessoas());
  }

  @Test
  public void testGetMesa() {
    Assertions.assertEquals(mesa, requisicao.getMesa());
  }

  @Test
  public void testSetMesa() {
    Mesa newMesa = new Mesa(2);
    requisicao.setMesa(newMesa);
    Assertions.assertEquals(newMesa, requisicao.getMesa());
  }

  @Test
  public void testGetIsAtendida() {
    Assertions.assertTrue(requisicao.getIsAtendida());
  }

  @Test
  public void testSetIsAtendida() {
    requisicao.setIsAtendida(true);
    Assertions.assertTrue(requisicao.getIsAtendida());
  }

  @Test
  public void testSetAtendida() {
    requisicao.setAtendida();
    Assertions.assertTrue(requisicao.getIsAtendida());
  }

  @Test
  public void testGetIsFinalizada() {
    Assertions.assertFalse(requisicao.getIsFinalizada());
  }

  @Test
  public void testSetIsFinalizada() {
    requisicao.setIsFinalizada(true);
    Assertions.assertTrue(requisicao.getIsFinalizada());
  }

  @Test
  public void testSetFinalizada() {
    requisicao.setFinalizada();
    Assertions.assertTrue(requisicao.getIsFinalizada());
  }

  @Test
  public void testGetDataHoraInicio() {
    Assertions.assertNull(requisicao.getDataHoraInicio());
  }

  @Test
  public void testSetDataHoraInicio() {
    LocalDateTime now = LocalDateTime.now();
    requisicao.setDataHoraInicio(now);
    Assertions.assertEquals(now, requisicao.getDataHoraInicio());
  }

  @Test
  public void testSetDataHoraInicioLocal() {
    requisicao.setDataHoraInicioLocal();
    Assertions.assertNotNull(requisicao.getDataHoraInicio());
  }

  @Test
  public void testGetDataHoraFim() {
    Assertions.assertNull(requisicao.getDataHoraFim());
  }

  @Test
  public void testSetDataHoraFim() {
    LocalDateTime now = LocalDateTime.now();
    requisicao.setDataHoraFim(now);
    Assertions.assertEquals(now, requisicao.getDataHoraFim());
  }

  @Test
  public void testSetDataHoraFimLocal() {
    requisicao.setDataHoraFimLocal();
    Assertions.assertNotNull(requisicao.getDataHoraFim());
  }

  @Test
  public void testGetTotalConta() {
    Assertions.assertEquals(0.0, requisicao.getTotalConta());
  }

  @Test
  public void testSetTotalConta() {
    requisicao.setTotalConta(100.0);
    Assertions.assertEquals(100.0, requisicao.getTotalConta());
  }

  @Test
  public void testGetTotalPorPessoa() {
    Assertions.assertEquals(0.0, requisicao.getTotalPorPessoa());
  }

  @Test
  public void testSetTotalPorPessoa() {
    requisicao.setTotalPorPessoa(25.0);
    Assertions.assertEquals(25.0, requisicao.getTotalPorPessoa());
  }

  @Test
  public void testSetPedidos() {
    List<Pedido> newPedidos = new ArrayList<>();
    Pedido pedido3 = new Pedido("normal");
    Pedido pedido4 = new Pedido("normal");
    newPedidos.add(pedido3);
    newPedidos.add(pedido4);

    requisicao.setPedidos(newPedidos);
    Assertions.assertEquals(newPedidos, requisicao.getPedidos());
  }

  @Test
  public void testAddPedido() {
    Pedido pedido3 = new Pedido("normal");
    requisicao.addPedido(pedido3);
    Assertions.assertTrue(requisicao.getPedidos().contains(pedido3));
  }

  @Test
  public void testRemovePedido() {
    requisicao.removePedido(pedido1);
    Assertions.assertFalse(requisicao.getPedidos().contains(pedido1));
  }

  @Test
  public void testIniciarRequisicao() {
    requisicao.iniciarRequisicao(mesa);
    Assertions.assertNotNull(requisicao.getDataHoraInicio());
    Assertions.assertTrue(requisicao.getIsAtendida());
    Assertions.assertTrue(mesa.getIsOcupada());
  }

  @Test
  public void testFinalizarRequisicao() {
    requisicao.finalizarRequisicao();
    Assertions.assertNotNull(requisicao.getDataHoraFim());
    Assertions.assertTrue(requisicao.getIsFinalizada());
    Assertions.assertFalse(mesa.getIsOcupada());
  }

  @Test
  public void testCancelarRequisicao() {
    requisicao.cancelarRequisicao();
    Assertions.assertTrue(requisicao.getIsFinalizada());
  }
}