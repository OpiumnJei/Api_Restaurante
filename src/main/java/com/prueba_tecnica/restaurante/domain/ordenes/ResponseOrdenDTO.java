package com.prueba_tecnica.restaurante.domain.ordenes;

import com.prueba_tecnica.restaurante.domain.platos.Plato;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

//DTO para el metodo put
public record ResponseOrdenDTO(
        Long id,
        Long mesaId,
        List<Long> platosSeleccionados,
        BigDecimal subtotal,
        EstadoOrden estadoOrden

) {
    //constructor usado para la paginacion
    public ResponseOrdenDTO(Orden orden) {
        this(orden.getId(), orden.getMesa().getId(),
                orden.getPlatosSeleccionados()
                        .stream()
                        .map(Plato::getId)
                        .toList(),
                orden.getSubtotal(),
                orden.getEstado()
        );
    }
}
