package com.luizpsg.advanced.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luizpsg.advanced.entities.Requisicao;
import com.luizpsg.advanced.services.RequisicaoService;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

  @Autowired
  private RequisicaoService requisicaoService;

  @GetMapping
  public ResponseEntity<List<Requisicao>> findAll() {
    List<Requisicao> list = requisicaoService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Requisicao>> findById(@PathVariable Long id) {
    Optional<Requisicao> obj = requisicaoService.findById(id);
    return ResponseEntity.ok().body(obj);
  }

  @PostMapping
  public ResponseEntity<Requisicao> insert(@RequestBody Requisicao obj) {
    obj = requisicaoService.insert(obj);
    return ResponseEntity.ok().body(obj);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Requisicao> update(@PathVariable Long id, @RequestBody Requisicao obj) {
    obj = requisicaoService.update(id, obj);
    return ResponseEntity.ok().body(obj);
  }

  @PutMapping("/atualizarFilaDeRequisicoes")
  public ResponseEntity<Void> atualizarFilaDeRequisicoes() {
    requisicaoService.atualizarFilaDeRequisicoes();
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    requisicaoService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
