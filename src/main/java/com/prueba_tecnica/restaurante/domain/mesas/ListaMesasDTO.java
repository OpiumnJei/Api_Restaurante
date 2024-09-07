package com.prueba_tecnica.restaurante.domain.mesas;

public record ListaMesasDTO(
        Long id,
        Integer cantidadMaxPersonas,
        String descripcionMesa,
        EstadoMesa estadoMesa
)

{
    //se mapean datos de la entidad en el dto a ser paginado(que se va a mostrar al usuario)
    public ListaMesasDTO(Mesa mesa) {
        this(mesa.getId(), mesa.getCantidadMaxPersonas(), mesa.getDescripcionMesa(), mesa.getEstadoMesa());
    }
}
