package com.advanced.comidinhasveganas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advanced.comidinhasveganas.entities.Pedido;
import com.advanced.comidinhasveganas.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  @Autowired
  private PedidoService pedidoService;

  @GetMapping
  public ResponseEntity<List<Pedido>> findAll() {
    return ResponseEntity.ok(pedidoService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> findById(@PathVariable Long id) {
    return ResponseEntity.ok()
        .body(pedidoService.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado")));
  }

  @PostMapping
  public ResponseEntity<Pedido> insert(@RequestBody Pedido pedido) {
    return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.insert(pedido));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    pedidoService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedido) {
    return ResponseEntity.ok(pedidoService.update(id, pedido));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    pedidoService.deleteAll();
    return ResponseEntity.noContent().build();
  }

}
