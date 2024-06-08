package com.luizpsg.advanced.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luizpsg.advanced.entities.Cliente;
import com.luizpsg.advanced.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Order(3) // Definindo a ordem de execução
public class InitializeClientesRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(InitializeClientesRunner.class);

  @Autowired
  private ClienteService clienteService;

  @Override
  public void run(String... args) throws Exception {
    if (clienteService.findAll().isEmpty()) {
      logger.info("Inicializando clientes...");
      inicializarClientes();
    } else {
      logger.info("Clientes já estão inicializados.");
    }
  }

  private void inicializarClientes() {
    Cliente[] clientes = {
        new Cliente(null, "Alice", "111111111"),
        new Cliente(null, "Bob", "222222222"),
        new Cliente(null, "Charlie", "333333333"),
        new Cliente(null, "Dave", "444444444"),
        new Cliente(null, "Eve", "555555555"),
        new Cliente(null, "Frank", "666666666"),
        new Cliente(null, "Grace", "777777777"),
        new Cliente(null, "Heidi", "888888888"),
        new Cliente(null, "Ivan", "999999999"),
        new Cliente(null, "Judy", "101010101")
    };
    for (Cliente cliente : clientes) {
      clienteService.insert(cliente);
    }
  }
}
