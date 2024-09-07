package com.prueba_tecnica.restaurante.domain.platos;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public record DatosPlatoDTO(

        Long id,
        @NotBlank
        String nombre,
        @NotNull
        Integer precio,
        @NotNull
        int cantidadPersonas,
        @NotNull
                @Size(min = 1) //se asegura que el usuario envie almenos un elemento en el arreglo
        List<Long> ingredientesIds,
        @NotNull
        CategoriaPlato categoriaPlato
) {
}
