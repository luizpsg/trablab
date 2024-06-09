package com.luizpsg.advanced.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luizpsg.advanced.entities.Pedido;
import com.luizpsg.advanced.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  @Autowired
  private PedidoService pedidoService;

  @GetMapping
  public ResponseEntity<List<Pedido>> findAll() {
    List<Pedido> list = pedidoService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Pedido>> findById(@PathVariable Long id) {
    Optional<Pedido> obj = pedidoService.findById(id);
    return ResponseEntity.ok().body(obj);
  }

  @PostMapping("/{RequisicaoId}")
  public ResponseEntity<Pedido> insert(@PathVariable Long RequisicaoId, @RequestBody Pedido obj) {
    obj = pedidoService.insert(obj, RequisicaoId);
    return ResponseEntity.ok().body(obj);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    pedidoService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
