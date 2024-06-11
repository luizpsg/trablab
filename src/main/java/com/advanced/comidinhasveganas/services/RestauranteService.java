package com.advanced.comidinhasveganas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advanced.comidinhasveganas.entities.Restaurante;
import com.advanced.comidinhasveganas.exceptions.ResourceNotFoundException;
import com.advanced.comidinhasveganas.repositories.RestauranteRepository;

@Service
public class RestauranteService {

  @Autowired
  private RestauranteRepository restauranteRepository;

  public List<Restaurante> findAll() {
    return restauranteRepository.findAll();
  }

  public Optional<Restaurante> findById(Long id) {
    return restauranteRepository.findById(id);
  }

  @Transactional
  public Restaurante insert(Restaurante restaurante) {
    return restauranteRepository.save(restaurante);
  }

  @Transactional
  public void deleteAll() {
    restauranteRepository.deleteAll();
  }

  @Transactional
  public void deleteById(Long id) {
    restauranteRepository.deleteById(id);
  }

  @Transactional
  public Restaurante update(Long id, Restaurante restaurante) {
    Restaurante entity = restauranteRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
    updateData(entity, restaurante);
    return restauranteRepository.save(entity);
  }

  private void updateData(Restaurante entity, Restaurante restaurante) {
    if (restaurante.getNome() != null) {
      entity.setNome(restaurante.getNome());
    }
    if (restaurante.getEndereco() != null) {
      entity.setEndereco(restaurante.getEndereco());
    }
  }

}