package com.prueba_tecnica.restaurante.domain.ingredientes;

public record ListaIngredientesDTO(Long id, String nombre) {
    public ListaIngredientesDTO(Ingrediente ingrediente) {
        this(ingrediente.getId(), ingrediente.getNombre());
    }
}
