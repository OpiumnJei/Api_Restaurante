package com.prueba_tecnica.restaurante.domain.ingredientes;

import jakarta.validation.constraints.NotNull;

public record DatosIngredientesDTO(
        Long id,
        @NotNull
        String nombre) {
}
