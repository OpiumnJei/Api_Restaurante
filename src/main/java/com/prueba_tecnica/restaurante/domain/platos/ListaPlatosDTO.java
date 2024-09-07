package com.prueba_tecnica.restaurante.domain.platos;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;

import java.util.List;

public record ListaPlatosDTO(Long id, String nombre, Integer precio, int cantidadPersonas, List<Long> ingredientesIds, CategoriaPlato categoriaPlato) {

    public ListaPlatosDTO(Plato plato) {
       this(plato.getId(),
               plato.getNombre(),
               plato.getPrecio(),
               plato.getCantidadPersonas(),
               plato.getIngredientes() //se obtienen los ingredientes(nombre, id)
                       .stream()
                       .map(Ingrediente::getId) //se mapean unicamento los ids(Long) de cada ingrediente
                       .toList(), //luego se pasan los datos(ids) a una lista
               plato.getCategoriaPlato()
       );
    }

}
