package com.prueba_tecnica.restaurante.domain.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosUsuariosDTO(

         @NotBlank
         String nombreUsuario,
         @NotBlank
         String contrasenia,
         @NotNull
         RolUsuario rolUsuario
) {
}
