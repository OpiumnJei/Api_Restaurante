package com.prueba_tecnica.restaurante.domain.ordenes;

import java.util.List;

public record ActualizarOrdenDTO(
        List<Long> platosAgregar,
        List<Long> platosEliminar
) {
}
