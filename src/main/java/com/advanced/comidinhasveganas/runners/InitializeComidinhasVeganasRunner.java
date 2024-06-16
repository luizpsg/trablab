package com.advanced.comidinhasveganas.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Restaurante;
import com.advanced.comidinhasveganas.services.ClienteService;
import com.advanced.comidinhasveganas.services.MesaService;
import com.advanced.comidinhasveganas.services.RestauranteService;

@Component
@Order(1) // Definindo a ordem de execução
public class InitializeComidinhasVeganasRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(InitializeComidinhasVeganasRunner.class);

  @Autowired
  private RestauranteService restauranteService;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private MesaService mesaService;

  @Override
  public void run(String... args) throws Exception {
    logger.info("Iniciando o restaurante Comidinhas Veganas");
    inicializarRestaurante();
  }

  private void inicializarRestaurante() {

    Restaurante restaurante = new Restaurante("Comidinhas Veganas", "Rua das Flores, 123");
    Restaurante restaurante2 = new Restaurante("Comidinhas Veganas 2", "Rua das Flores, 123");

    Cliente[] clientes = {
        new Cliente("Victor", "1", restaurante),
        new Cliente("Marcos Rocha", "2", restaurante),
        new Cliente("Leonardo Silva", "3", restaurante),
        new Cliente("Réver", "4", restaurante),
        new Cliente("Junior César", "6", restaurante),
        new Cliente("Pierre", "5", restaurante),
        new Cliente("Leandro Donizete", "8", restaurante),
        new Cliente("Ronaldinho", "10", restaurante),
        new Cliente("Diego Tardelli", "9", restaurante),
        new Cliente("Bernard", "11", restaurante),
        new Cliente("Jô", "7", restaurante)
    };

    Mesa[] mesas = {
        new Mesa(4, restaurante),
        new Mesa(6, restaurante),
        new Mesa(8, restaurante),
        new Mesa(8, restaurante2)
    };

    restauranteService.insert(restaurante);
    restauranteService.insert(restaurante2);

    for (Cliente cliente : clientes) {
      clienteService.insert(cliente);
    }

    for (Mesa mesa : mesas) {
      mesaService.insert(mesa);
    }

  };
}