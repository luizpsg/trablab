package com.advanced.comidinhasveganas.runners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.services.ClienteService;
import com.advanced.comidinhasveganas.services.MesaService;

@Component
@Order(12)
public class InitializeConfigRunner implements CommandLineRunner {

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private MesaService mesaService;

  @Override
  public void run(String... args) throws Exception {

    Cliente cliente = new Cliente("lp", "123");

    clienteService.insert(cliente);

    List<Cliente> clientes = clienteService.findAll();

    clientes.stream().forEach(System.out::println);

    // clienteService.update(15L, cliente);

    Mesa mesa = new Mesa(12);

    mesaService.insert(mesa);

    List<Mesa> mesas = mesaService.findAll();

    mesas.stream().forEach(System.out::println);
  }

}
