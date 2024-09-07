package com.prueba_tecnica.restaurante.domain.platos;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;

import java.util.List;
//dto para actualizar ingredientes de un plato
public record ActualizarPlatoDTO(
         List<Long> ingredientesIdsParaAgregar,
         List<Long> ingredientesIdsParaBorrar
) {
//    public ActualizarPlatoDTO(Long id, String nombre, int cantidadPersonas, List<Ingrediente> ingredientes, CategoriaPlato categoriaPlato) {
//
//    }
}
