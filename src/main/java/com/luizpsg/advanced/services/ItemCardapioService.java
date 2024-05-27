package com.luizpsg.advanced.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizpsg.advanced.entities.ItemCardapio;
import com.luizpsg.advanced.repositories.ItemCardapioRepository;

@Service
public class ItemCardapioService {

  @Autowired
  private ItemCardapioRepository repository;

  public List<ItemCardapio> findAll() {
    return repository.findAll();
  }

  public Optional<ItemCardapio> findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public ItemCardapio insert(ItemCardapio item) {
    return repository.save(item);
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Transactional
  public ItemCardapio update(Long id, ItemCardapio item) {
    ItemCardapio entity = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item do cardápio não encontrado"));
    updateData(entity, item);
    return repository.save(entity);
  }

  private void updateData(ItemCardapio entity, ItemCardapio item) {
    entity.setNome(item.getNome());
    entity.setPreco(item.getPreco());
  }
}
