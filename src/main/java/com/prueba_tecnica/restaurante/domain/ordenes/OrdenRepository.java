package com.prueba_tecnica.restaurante.domain.ordenes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    //obtiene una lista con el id de la mesa y las ordenes con estado
    List<Orden> findByMesaIdAndEstado(Long mesaId, EstadoOrden estado);
}
