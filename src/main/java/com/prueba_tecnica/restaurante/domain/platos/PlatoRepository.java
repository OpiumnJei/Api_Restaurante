package com.prueba_tecnica.restaurante.domain.platos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<Plato, Long> {
    Boolean existsByNombre(String nombre);
}
