package com.luizpsg.advanced.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luizpsg.advanced.entities.Mesa;
import com.luizpsg.advanced.services.MesaService;

@Component
@Order(1)
public class InitializeMesasRunner implements CommandLineRunner {

  @Autowired
  private MesaService mesaService;

  @Override
  public void run(String... args) throws Exception {
    if (mesaService.findAll().isEmpty()) {
      inicializarMesas();
    }
  }

  private void inicializarMesas() {
    int[] lugaresPorMesa = { 4, 4, 4, 4, 6, 6, 6, 6, 8, 8 };
    for (int lugares : lugaresPorMesa) {
      Mesa mesa = new Mesa(null, lugares, false);
      mesaService.insert(mesa);
    }
  }
}
