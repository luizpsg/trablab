package com.advanced.comidinhasveganas.runners;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.advanced.comidinhasveganas.entities.Cardapio;
import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.ItemCardapio;
import com.advanced.comidinhasveganas.entities.ItemPedido;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Pedido;
import com.advanced.comidinhasveganas.entities.Requisicao;
import com.advanced.comidinhasveganas.entities.Restaurante;
import com.advanced.comidinhasveganas.exceptions.ResourceNotFoundException;
import com.advanced.comidinhasveganas.services.CardapioService;
import com.advanced.comidinhasveganas.services.ClienteService;
import com.advanced.comidinhasveganas.services.ItemCardapioService;
import com.advanced.comidinhasveganas.services.ItemPedidoService;
import com.advanced.comidinhasveganas.services.MesaService;
import com.advanced.comidinhasveganas.services.PedidoService;
import com.advanced.comidinhasveganas.services.RequisicaoService;
import com.advanced.comidinhasveganas.services.RestauranteService;

@Component
@Order(12)
public class InitializeConfigRunner implements CommandLineRunner {

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private MesaService mesaService;

  @Autowired
  private RestauranteService restauranteService;

  @Autowired
  private RequisicaoService requisicaoService;

  @Autowired
  private CardapioService cardapioService;

  @Autowired
  private ItemCardapioService itemCardapioService;

  @Autowired
  private PedidoService pedidoService;

  @Autowired
  private ItemPedidoService itemPedidoService;

  @Override
  public void run(String... args) throws Exception {

    Long restauranteId = 1L;

    Restaurante restaurante = restauranteService.findById(restauranteId)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

    List<Mesa> mesas = mesaService.findByRestauranteId(restaurante.getId());
    restaurante.setMesas(mesas);
    List<Cliente> clientes = clienteService.findByRestauranteId(restaurante.getId());
    restaurante.setClientes(clientes);
    List<Requisicao> requisicoes = requisicaoService.findByRestauranteId(restaurante.getId());
    restaurante.setRequisicoes(requisicoes);
    List<Cardapio> cardapios = cardapioService.findByRestauranteId(restaurante.getId());
    List<ItemCardapio> itensCardapio = itemCardapioService.findByCardapioRestauranteId(restaurante.getId());
    List<Pedido> pedidos = pedidoService.findByRequisicaoRestauranteId(restaurante.getId());
    List<ItemPedido> itensPedido = itemPedidoService.findByPedidoRequisicaoRestauranteId(restaurante.getId());

    cardapios.forEach(c -> {
      List<ItemCardapio> itens = itensCardapio.stream()
          .filter(i -> i.getCardapio().getId().equals(c.getId()))
          .collect(Collectors.toList());
      c.setItens(itens);
    });

    restaurante.setCardapios(cardapios);

    pedidos.forEach(p -> {
      List<ItemPedido> itens = itensPedido.stream()
          .filter(i -> i.getPedido().getId().equals(p.getId()))
          .collect(Collectors.toList());
      p.setItens(itens);
    });

    System.out.println("-------------------");
    System.out.println(restaurante);

    restaurante.getMesas().stream().forEach(m -> System.out.println(m));
    System.out.println("-------------------");
    restaurante.getClientes().stream().forEach(c -> System.out.println(c));
    System.out.println("-------------------");
    restaurante.getRequisicoes().stream().forEach(r -> System.out.println(r));
    System.out.println("-------------------");
    restaurante.getCardapios().stream().forEach(c -> System.out.println(c));
    System.out.println("-------------------");
    cardapios.stream().forEach(c -> c.getItens().stream().forEach(i -> System.out.println(i)));

    // Receber um cliente (exemplo de dados)
    String nomeCliente = "João da Silva";
    String telefoneCliente = "1";

    Cliente cliente = restaurante.getClienteByTelefone(telefoneCliente);
    if (cliente == null) {
      cliente = new Cliente(nomeCliente, telefoneCliente, restaurante);
      clientes.add(cliente);
      restaurante.addCliente(cliente);
    }

    Requisicao req = new Requisicao(cliente, 4, restaurante);
    restaurante.addRequisicao(req);
    requisicoes.add(req);
    System.out.println(req);

    restaurante.atualizarRequisicoes();

    req.setPedido(new Pedido());

    ItemCardapio itemCardapio = cardapios.get(0).getItens().get(0);
    ItemPedido itemPedido = new ItemPedido(itemCardapio, 2, req.getPedido());
    req.getPedido().addItem(itemPedido);
    itensPedido.add(itemPedido);

    itemCardapio = cardapios.get(0).getItens().get(1);
    itemPedido = new ItemPedido(itemCardapio, 1, req.getPedido());
    req.getPedido().addItem(itemPedido);
    itensPedido.add(itemPedido);

    pedidos.add(req.getPedido());

    clientes.stream().forEach(c -> Optional.ofNullable(c.getId()).ifPresentOrElse(
        id -> clienteService.update(id, c),
        () -> clienteService.insert(c)));

    mesas.stream().forEach(m -> Optional.ofNullable(m.getId()).ifPresentOrElse(
        id -> mesaService.update(id, m),
        () -> mesaService.insert(m)));

    requisicoes.stream().forEach(r -> Optional.ofNullable(r.getId()).ifPresentOrElse(
        id -> requisicaoService.update(id, r),
        () -> requisicaoService.insert(r)));

    pedidos.stream().forEach(p -> Optional.ofNullable(p.getId()).ifPresentOrElse(
        id -> pedidoService.update(id, p),
        () -> pedidoService.insert(p)));

    itensPedido.stream().forEach(i -> Optional.ofNullable(i.getId()).ifPresentOrElse(
        id -> itemPedidoService.update(id, i),
        () -> itemPedidoService.insert(i)));

    cardapios.stream().forEach(c -> Optional.ofNullable(c.getId()).ifPresentOrElse(
        id -> cardapioService.update(id, c),
        () -> cardapioService.insert(c)));

    itensCardapio.stream().forEach(i -> Optional.ofNullable(i.getId()).ifPresentOrElse(
        id -> itemCardapioService.update(id, i),
        () -> itemCardapioService.insert(i)));

    restauranteService.update(restaurante.getId(), restaurante);

    // restaurante.setMesas(mesas);
    // restaurante.setClientes(clientes);
    // restaurante.setRequisicoes(requisicoes);
    // restaurante.setCardapios(cardapios);

    // Cliente c2 = restaurante.getClienteByTelefone("432");
    // if (c2 == null) {
    // c2 = new Cliente("Maria", "432", restaurante);
    // clientes.add(c2);
    // restaurante.addCliente(c2);
    // }

    // Cliente c3 = restaurante.getClienteByTelefone("3");
    // if (c3 == null) {
    // c3 = new Cliente("João", "3", restaurante);
    // clientes.add(c3);
    // restaurante.addCliente(c3);
    // }

    // Requisicao req = new Requisicao(c2, 4, restaurante);
    // System.out.println(req);

    // System.out.println("-------------------");

    // Requisicao req2 = new Requisicao(c3, 2, restaurante);
    // System.out.println(req2);

    // restaurante.addRequisicao(req);
    // restaurante.addRequisicao(req2);

    // restaurante.atualizarRequisicoes();

    // restaurante.finalizarRequisicao(req2);

    // restaurante.getRequisicoes().stream().forEach(r -> System.out.println(r));

    // clientes.stream().forEach(c ->
    // Optional.ofNullable(c.getId()).ifPresentOrElse(
    // id -> clienteService.update(id, c),
    // () -> clienteService.insert(c)));

    // mesas.stream().forEach(m -> Optional.ofNullable(m.getId()).ifPresentOrElse(
    // id -> mesaService.update(id, m),
    // () -> mesaService.insert(m)));

    // requisicoes.stream().forEach(r ->
    // Optional.ofNullable(r.getId()).ifPresentOrElse(
    // id -> requisicaoService.update(id, r),
    // () -> requisicaoService.insert(r)));

    // cardapios.stream().forEach(c ->
    // Optional.ofNullable(c.getId()).ifPresentOrElse(
    // id -> cardapioService.update(id, c),
    // () -> cardapioService.insert(c)));

    // restauranteService.update(restaurante.getId(), restaurante);
  }

}
