package com.prueba_tecnica.restaurante.domain.mesas;

public record ActualizarMesaDTO(Long id,
                                Integer cantidadMaxPersonas,
                                String descripcionMesa,
                                EstadoMesa estadoMesa) {
}
