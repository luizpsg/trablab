package com.advanced.comidinhasveganas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.ItemCardapio;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Pedido;
import com.advanced.comidinhasveganas.entities.Requisicao;
import com.advanced.comidinhasveganas.services.RestauranteService;

@SpringBootApplication
public class RestauranteApplication implements CommandLineRunner {
  @Autowired
  private RestauranteService restauranteService;

  public static void main(String[] args) {
    SpringApplication.run(RestauranteApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("1. Adicionar Mesa");
      System.out.println("2. Adicionar Requisição");
      System.out.println("3. Adicionar Pedido");
      System.out.println("4. Alocar Clientes");
      System.out.println("5. Imprimir Conta");
      System.out.println("6. Iniciar Restaurante");
      System.out.println("0. Sair");

      int escolha = scanner.nextInt();
      scanner.nextLine(); // Consumir a nova linha

      switch (escolha) {
        case 1:
          // Adicionar mesa
          System.out.println("Capacidade da mesa:");
          int capacidade = scanner.nextInt();
          Mesa mesa = new Mesa(null, capacidade, 0);
          restauranteService.addMesa(mesa);
          break;
        case 2:
          // Adicionar requisição
          System.out.println("Nome do cliente:");
          String nomeCliente = scanner.nextLine();
          Cliente cliente = new Cliente();
          cliente.setNome(nomeCliente);
          restauranteService.addCliente(cliente);
          System.out.println("Número de pessoas:");
          int numeroPessoas = scanner.nextInt();
          Requisicao requisicao = new Requisicao(null, LocalDateTime.now(), null, cliente, numeroPessoas, false, null,
              0, new ArrayList<>());
          restauranteService.addRequisicao(requisicao);
          break;
        case 3:
          // Adicionar pedido
          System.out.println("ID da Requisição:");
          Long requisicaoId = scanner.nextLong();
          scanner.nextLine();
          System.out.println("Nome do Item do Cardápio:");
          String nomeItem = scanner.nextLine();
          ItemCardapio item = restauranteService.obterItemCardapioPorNome(nomeItem);
          if (item != null) {
            Pedido pedido = new Pedido();
            pedido.setRequisicao(restauranteService.obterRequisicaoPorId(requisicaoId));
            pedido.addItem(item);
            restauranteService.addPedido(pedido);
          } else {
            System.out.println("Item do cardápio não encontrado.");
          }
          break;
        case 4:
          // Alocar clientes
          restauranteService.alocarClientes();
          break;
        case 5:
          // Imprimir conta
          System.out.println("ID da Requisição:");
          Long id = scanner.nextLong();
          restauranteService.printConta(id);
          break;
        case 6:
          // Iniciar restaurante
          restauranteService.iniciarRestaurante();
          break;
        case 0:
          // Sair
          System.exit(0);
        default:
          System.out.println("Opção inválida");
      }
      scanner.close();
    }
  }
}
