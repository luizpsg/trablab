package com.advanced.comidinhasveganas.runners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Restaurante;
import com.advanced.comidinhasveganas.services.MesaService;
import com.advanced.comidinhasveganas.services.RestauranteService;

@Component
@Order(99)
public class InitializeRestauranteRunner implements CommandLineRunner {

  @Autowired
  private RestauranteService restauranteService;

  @Autowired
  private MesaService mesaService;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    Restaurante restaurante = inicializarRestaurante("Comidinhas Veganas", "Rua das Flores, 123");
    addMesas(restaurante);
    restauranteService.insert(restaurante);
  }

  private Restaurante inicializarRestaurante(String nome, String end) {
    return new Restaurante(nome, end);
  }

  private void addMesas(Restaurante restaurante) {
    List<Mesa> mesas = mesaService.findAll();
    mesas.stream().forEach(m -> restaurante.addMesa(m));
  }

}
