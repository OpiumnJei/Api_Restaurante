package com.prueba_tecnica.restaurante.domain.ordenes;

import com.prueba_tecnica.restaurante.domain.platos.DatosPlatoDTO;
import com.prueba_tecnica.restaurante.domain.platos.Plato;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record DatosOrdenDTO(

        Long id,
        @NotNull
        Long mesaId,
        @NotEmpty
                @Size(min = 1)//se asegura de que almenos se envie un id de un plato
        List<Long> platosSeleccionados,

        BigDecimal subtotal,
        EstadoOrden estadoOrden

) {
}
