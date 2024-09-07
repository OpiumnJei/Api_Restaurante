package com.prueba_tecnica.restaurante.domain.ingredientes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    Boolean existsByNombre(String nombre);
}
