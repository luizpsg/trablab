package com.luizpsg.advanced;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luizpsg.advanced.entities.Cliente;
import com.luizpsg.advanced.entities.ItemCardapio;
import com.luizpsg.advanced.entities.Mesa;
import com.luizpsg.advanced.entities.Pedido;
import com.luizpsg.advanced.entities.Requisicao;
import com.luizpsg.advanced.services.ClienteService;
import com.luizpsg.advanced.services.ItemCardapioService;
import com.luizpsg.advanced.services.MesaService;
import com.luizpsg.advanced.services.PedidoService;
import com.luizpsg.advanced.services.RequisicaoService;

@SpringBootApplication
public class AdvancedApplication implements CommandLineRunner {

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private RequisicaoService requisicaoService;

  @Autowired
  private MesaService mesaService;

  @Autowired
  private ItemCardapioService itemCardapioService;

  @Autowired
  private PedidoService pedidoService;

  public static void main(String[] args) {
    SpringApplication.run(AdvancedApplication.class, args);
  }

  @Override
  public void run(String... args) {
    try (Scanner sc = new Scanner(System.in)) {
      inicializarMesas();
      inicializarCardapio();

      Cliente cliente1 = cadastrarOuObterCliente(sc, "123456789", "Luiz");
      criarRequisicao(cliente1, 4);

      Cliente cliente2 = cadastrarOuObterCliente(sc, "12312", "Paulo");
      criarRequisicao(cliente2, 7);

      atualizarFilaDeRequisicoes();

      processarPedidos(sc);

      listarPedidos();

      encerrarConta(sc);
    } catch (Exception e) {
      System.out.println("Ocorreu um erro: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void inicializarMesas() {
    int[] lugaresPorMesa = { 4, 4, 4, 4, 6, 6, 6, 6, 8, 8 };
    for (int lugares : lugaresPorMesa) {
      Mesa mesa = new Mesa(null, lugares, false);
      mesaService.insert(mesa);
    }
  }

  private void inicializarCardapio() {
    ItemCardapio[] itens = {
        new ItemCardapio(null, "Moqueca de Palmito", 32.0),
        new ItemCardapio(null, "Falafel Assado", 20.0),
        new ItemCardapio(null, "Salada Primavera com Macarrão Konjac", 25.0),
        new ItemCardapio(null, "Escondidinho de Inhame", 18.0),
        new ItemCardapio(null, "Strogonoff de Cogumelos", 35.0),
        new ItemCardapio(null, "Caçarola de legumes", 22.0),
        new ItemCardapio(null, "Água", 3.0),
        new ItemCardapio(null, "Copo de Suco", 7.0),
        new ItemCardapio(null, "Refrigerante Orgânico", 7.0),
        new ItemCardapio(null, "Cerveja Vegana", 9.0),
        new ItemCardapio(null, "Taça de Vinho Vegano", 18.0)
    };
    for (ItemCardapio item : itens) {
      itemCardapioService.insert(item);
    }
  }

  private Cliente cadastrarOuObterCliente(Scanner sc, String telefone, String nome) {
    return clienteService.findByTelefone(telefone)
        .orElseGet(() -> {
          Cliente cliente = new Cliente(null, nome, telefone);
          return clienteService.insert(cliente);
        });
  }

  private Requisicao criarRequisicao(Cliente cliente, int quantidadePessoas) {
    Requisicao req = new Requisicao(null, cliente, quantidadePessoas, null, false, false, null, new ArrayList<>());
    return requisicaoService.insert(req);
  }

  private void atualizarFilaDeRequisicoes() {
    requisicaoService.findAll().stream()
        .filter(req -> !req.isAtendida())
        .forEach(req -> {
          Mesa mesa = mesaService.findMesaDisponivel(req.getQuantidadePessoas())
              .orElse(null);
          if (mesa != null) {
            atribuirMesaARequisicao(req, mesa);
          } else {
            System.out.println("Não há mesas disponíveis no momento, você está na fila.");
          }
        });
  }

  private void atribuirMesaARequisicao(Requisicao req, Mesa mesa) {
    mesa.setOcupada(true);
    req.setMesa(mesa);
    req.setAtendida(true);
    req.setDataHoraInicio(LocalDateTime.now());
    requisicaoService.update(req.getId(), req);
    mesaService.update(mesa.getId(), mesa);
    System.out.println("Mesa atribuída: " + mesa);
  }

  private void processarPedidos(Scanner sc) {
    Requisicao req = getRequisicaoDaMesaSelecionada(sc);
    boolean continuar = true;
    while (continuar) {
      System.out.println(imprimirCardapio());
      Long idItemCardapio = receberIdItemCardapio(sc);
      int quantidade = receberQuantidade(sc);
      sc.nextLine(); // Consome o restante da linha

      if (req != null) {
        adicionarItemAoPedido(req, idItemCardapio, quantidade);
        System.out.println("Total atualizado: " + req.getTotalConta());
      }

      System.out.println("Deseja continuar? (s/n)");
      continuar = !sc.nextLine().equalsIgnoreCase("n");
    }
  }

  private Requisicao getRequisicaoDaMesaSelecionada(Scanner sc) {
    System.out.println("Mesas ocupadas:");
    System.out.println(listarMesasOcupadas());
    System.out.println("Selecione a mesa para adicionar pedidos:");
    Long idMesa = sc.nextLong();
    sc.nextLine(); // Consome o restante da linha
    return requisicaoService.findRequisicaoByMesaId(idMesa).orElse(null);
  }

  private Long receberIdItemCardapio(Scanner sc) {
    System.out.println("Digite o id do item do cardápio:");
    return sc.nextLong();
  }

  private int receberQuantidade(Scanner sc) {
    System.out.println("Digite a quantidade:");
    return sc.nextInt();
  }

  private void adicionarItemAoPedido(Requisicao req, Long idItemCardapio, int quantidade) {
    ItemCardapio item = itemCardapioService.findById(idItemCardapio)
        .orElseThrow(() -> new RuntimeException("Item do cardápio não encontrado"));
    Pedido pedido = new Pedido(null, item, quantidade);
    req.addPedido(pedido);
    atualizarTotalRequisicao(req);
    requisicaoService.update(req.getId(), req);
  }

  private void atualizarTotalRequisicao(Requisicao req) {
    double total = req.getPedidos().stream()
        .mapToDouble(p -> p.getItem().getPreco() * p.getQuantidade())
        .sum();
    total += total * 0.10; // Adiciona a taxa de serviço de 10%
    req.setTotalConta(total);
    req.setTotalPorPessoa(total / req.getQuantidadePessoas());
  }

  private String imprimirCardapio() {
    return itemCardapioService.findAll().stream()
        .map(ItemCardapio::toString)
        .collect(Collectors.joining("\n"));
  }

  private String listarMesasOcupadas() {
    return mesaService.findAll().stream()
        .filter(Mesa::isOcupada)
        .map(Mesa::toString)
        .collect(Collectors.joining("\n"));
  }

  private void listarPedidos() {
    pedidoService.findAll().forEach(System.out::println);
  }

  private void encerrarConta(Scanner sc) {
    System.out.println("Mesas ocupadas:");
    System.out.println(listarMesasOcupadas());
    System.out.println("Selecione a mesa para encerrar a conta:");
    Long idMesa = sc.nextLong();
    sc.nextLine(); // Consome o restante da linha

    Requisicao req = requisicaoService.findRequisicaoByMesaId(idMesa)
        .orElseThrow(() -> new RuntimeException("Requisição não encontrada para a mesa selecionada"));

    req.setFinalizada(true);
    req.setDataHoraFim(LocalDateTime.now());
    requisicaoService.update(req.getId(), req);

    Mesa mesa = req.getMesa();
    mesa.setOcupada(false);
    mesaService.update(mesa.getId(), mesa);

    System.out.println("Conta encerrada.");
    System.out.println("Total a pagar: " + req.getTotalConta());
    System.out.println("Total por pessoa: " + req.getTotalPorPessoa());
  }
}