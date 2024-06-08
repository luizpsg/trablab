package com.luizpsg.advanced.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luizpsg.advanced.entities.Mesa;
import com.luizpsg.advanced.services.MesaService;

@RestController
@RequestMapping("/mesas")
public class MesaController {

  @Autowired
  private MesaService mesaService;

  @GetMapping
  public ResponseEntity<List<Mesa>> findAll() {
    List<Mesa> list = mesaService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Mesa>> findById(@PathVariable Long id) {
    Optional<Mesa> obj = mesaService.findById(id);
    return ResponseEntity.ok().body(obj);
  }

  @PostMapping
  public ResponseEntity<Mesa> insert(@RequestBody Mesa obj) {
    obj = mesaService.insert(obj);
    return ResponseEntity.ok().body(obj);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Mesa> update(@PathVariable Long id, @RequestBody Mesa obj) {
    obj = mesaService.update(id, obj);
    return ResponseEntity.ok().body(obj);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    mesaService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
