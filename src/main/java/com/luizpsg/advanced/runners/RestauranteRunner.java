package com.luizpsg.advanced.runners;

import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luizpsg.advanced.entities.Cliente;
import com.luizpsg.advanced.services.ClienteService;
import com.luizpsg.advanced.services.ItemCardapioService;
import com.luizpsg.advanced.services.MesaService;
import com.luizpsg.advanced.services.PedidoService;
import com.luizpsg.advanced.services.RequisicaoService;

@Component
@Order(4) // Definindo a ordem de execução
public class RestauranteRunner implements CommandLineRunner {

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

  @Override
  public void run(String... args) {
    try (Scanner sc = new Scanner(System.in)) {
      boolean sair = false;
      while (!sair) {
        exibirMenu();
        int opcao = sc.nextInt();
        sc.nextLine(); // Consumir nova linha

        switch (opcao) {
          case 1:
            handleCadastrarCliente(sc);
            break;
          case 2:
            handleCriarRequisicao(sc);
            break;
          case 3:
            handleProcessarPedidos(sc);
            break;
          case 4:
            handleEncerrarConta(sc);
            break;
          case 5:
            listarPedidos();
            break;
          case 0:
            sair = true;
            System.exit(0);
          default:
            System.out.println("Opção inválida.");
        }
      }
    } catch (Exception e) {
      System.out.println("Ocorreu um erro: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void exibirMenu() {
    System.out.println("=== Sistema de Gerenciamento de Restaurante ===");
    System.out.println("1. Cadastrar Cliente");
    System.out.println("2. Criar Requisição");
    System.out.println("3. Adicionar Pedido");
    System.out.println("4. Encerrar Conta");
    System.out.println("5. Listar Pedidos");
    System.out.println("0. Sair");
    System.out.print("Escolha uma opção: ");
  }

  private void handleCadastrarCliente(Scanner sc) {
    System.out.print("Digite o telefone do cliente: ");
    String telefone = sc.nextLine();
    System.out.print("Digite o nome do cliente: ");
    String nome = sc.nextLine();

    Cliente cliente = clienteService.cadastrarCliente(nome, telefone);
    System.out.println("Cliente cadastrado: " + cliente);
  }

  private void handleCriarRequisicao(Scanner sc) {
    Cliente cliente = handleReceberCliente(sc);
    System.out.print("Digite a quantidade de pessoas: ");
    int quantidadePessoas = sc.nextInt();
    sc.nextLine(); // Consumir nova linha

    requisicaoService.criarRequisicao(cliente, quantidadePessoas);
    requisicaoService.atualizarFilaDeRequisicoes();
  }

  private Cliente handleReceberCliente(Scanner sc) {
    System.out.print("Digite o telefone do cliente: ");
    String telefone = sc.nextLine();
    return clienteService.findByTelefone(telefone)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
  }

  private void handleProcessarPedidos(Scanner sc) {
    System.out.print("Digite o id da requisição: ");
    Long idRequisicao = sc.nextLong();
    sc.nextLine(); // Consumir nova linha

    boolean continuar = true;
    while (continuar) {
      System.out.println(itemCardapioService.imprimirCardapio());
      Long idItemCardapio = receberIdItemCardapio(sc);
      int quantidade = receberQuantidade(sc);
      sc.nextLine(); // Consome o restante da linha

      pedidoService.adicionarItemAoPedido(idRequisicao, idItemCardapio, quantidade);

      System.out.print("Deseja continuar adicionando pedidos? (s/n): ");
      continuar = !sc.nextLine().equalsIgnoreCase("n");
    }
  }

  private Long receberIdItemCardapio(Scanner sc) {
    System.out.print("Digite o id do item do cardápio: ");
    return sc.nextLong();
  }

  private int receberQuantidade(Scanner sc) {
    System.out.print("Digite a quantidade: ");
    return sc.nextInt();
  }

  private void handleEncerrarConta(Scanner sc) {
    System.out.print("Selecione a mesa para encerrar a conta: ");
    Long idMesa = sc.nextLong();
    sc.nextLine(); // Consome o restante da linha

    requisicaoService.encerrarConta(idMesa);
  }

  private void listarPedidos() {
    pedidoService.findAll().forEach(System.out::println);
  }
}
