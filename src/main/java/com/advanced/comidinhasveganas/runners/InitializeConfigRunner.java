package com.advanced.comidinhasveganas.runners;

import java.util.ArrayList;
import java.util.List;

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
@Order(12)
public class InitializeConfigRunner implements CommandLineRunner {

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private MesaService mesaService;

  @Autowired
  private RestauranteService restauranteService;

  @Override
  public void run(String... args) throws Exception {

    Restaurante restaurante = new Restaurante("Comidinhas Veganas", "Rua das Flores, 123");

    List<Cliente> clientes = clienteService.findAll();

    // clientes.add(new Cliente("João", "11999998888"));
    // clientes.add(new Cliente("Maria", "11999997777"));
    // clientes.add(new Cliente("José", "11999996666"));

    List<Mesa> mesas = new ArrayList<>();

    mesas.add(new Mesa(4));
    mesas.add(new Mesa(4));
    mesas.add(new Mesa(4));

    restaurante.setClientes(clientes);
    restaurante.setMesas(mesas);

    restauranteService.insert(restaurante);
    clientes.stream().forEach(c -> c.setRestaurante(restaurante));
    clientes.stream().forEach(c -> clienteService.update(c.getId(), c));
    clientes.stream().forEach(System.out::println);

  }

}
