package com.prueba_tecnica.restaurante.domain.mesas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosMesaDTO(
        Long id,
        @NotNull
        Integer cantidadMaxPersonas,
        @NotBlank
        String descripcionMesa,
        EstadoMesa estadoMesa
) {
}
