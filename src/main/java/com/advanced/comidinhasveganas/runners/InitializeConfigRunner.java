package com.advanced.comidinhasveganas.runners;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.advanced.comidinhasveganas.entities.Cliente;
import com.advanced.comidinhasveganas.entities.Mesa;
import com.advanced.comidinhasveganas.entities.Requisicao;
import com.advanced.comidinhasveganas.entities.Restaurante;
import com.advanced.comidinhasveganas.services.ClienteService;
import com.advanced.comidinhasveganas.services.MesaService;
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

  @Override
  public void run(String... args) throws Exception {

    Restaurante restaurante = restauranteService.findAll().get(0);

    List<Mesa> mesas = mesaService.findByRestauranteId(restaurante.getId());
    List<Cliente> clientes = clienteService.findByRestauranteId(restaurante.getId());
    List<Requisicao> requisicoes = requisicaoService.findByRestauranteId(restaurante.getId());
    restaurante.setMesas(mesas);
    restaurante.setClientes(clientes);
    restaurante.setRequisicoes(requisicoes);

    Cliente c2 = restaurante.getClienteByTelefone("432");
    if (c2 == null) {
      c2 = new Cliente("Maria", "432", restaurante);
      clientes.add(c2);
      restaurante.addCliente(c2);
    }

    Cliente c3 = restaurante.getClienteByTelefone("3");
    if (c3 == null) {
      c3 = new Cliente("João", "3", restaurante);
      clientes.add(c3);
      restaurante.addCliente(c3);
    }

    Requisicao req = new Requisicao(c2, 4, restaurante);
    System.out.println(req);

    System.out.println("-------------------");

    Requisicao req2 = new Requisicao(c3, 2, restaurante);
    System.out.println(req2);

    restaurante.addRequisicao(req);
    restaurante.addRequisicao(req2);

    restaurante.atualizarRequisicoes();

    clientes.stream().forEach(c -> Optional.ofNullable(c.getId()).ifPresentOrElse(
        id -> clienteService.update(id, c),
        () -> clienteService.insert(c)));

    mesas.stream().forEach(m -> Optional.ofNullable(m.getId()).ifPresentOrElse(
        id -> mesaService.update(id, m),
        () -> mesaService.insert(m)));

    requisicoes.stream().forEach(r -> Optional.ofNullable(r.getId()).ifPresentOrElse(
        id -> requisicaoService.update(id, r),
        () -> requisicaoService.insert(r)));

    restauranteService.update(restaurante.getId(), restaurante);
  }

}
