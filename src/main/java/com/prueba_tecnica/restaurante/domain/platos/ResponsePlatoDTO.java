package com.prueba_tecnica.restaurante.domain.platos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

//Datos a mostrarse al usuario cuando se haga un Put
public record ResponsePlatoDTO(
        Long id,
        String nombre,
        Integer precio,
        int cantidadPersonas,
        List<Long> ingredientesIds,
        CategoriaPlato categoriaPlato
) {
}
