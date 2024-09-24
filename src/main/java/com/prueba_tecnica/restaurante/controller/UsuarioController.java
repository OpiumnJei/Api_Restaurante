package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.usuarios.DatosUsuariosDTO;
import com.prueba_tecnica.restaurante.domain.usuarios.Usuario;
import com.prueba_tecnica.restaurante.domain.usuarios.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestion de usuarios y roles.")
@SecurityRequirement(name = "bearer-key")//se usa para indicar que un endpoint específico requiere autenticación o autorización
@RestController
@RequestMapping("/restaurante/registro-usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crear un mesero", description = "Crear un usuario con el rol de mesero")
    @PostMapping("/mesero")
    @PreAuthorize("hasRole('ADMINISTRADOR')")//antes de llamar al metodo verificar si el usuario es un administrador
    public ResponseEntity<String> registrarMesero(@RequestBody @Valid DatosUsuariosDTO datosUsuariosDTO, Authentication authentication) {
        //verificar que el usuario tenga el rol de administrador
        if(hasRole(authentication, "ADMINISTRADOR")){
            usuarioService.crearUsuarioMesero(datosUsuariosDTO);
            return ResponseEntity.ok("Usuario creado con exito!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para registrar un mesero");
    }

    @Operation(summary = "Crear un administrador", description = "Crear un usuario con el rol de administrador.")
    @PostMapping("/administrador")
//    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> registrarAdministrador(@RequestBody @Valid DatosUsuariosDTO datosUsuariosDTO, Authentication authentication) {
        // Verifica si el usuario actual sea un administrador
        if (hasRole(authentication, "ADMINISTRADOR")) {
            usuarioService.crearUsuarioAdmin(datosUsuariosDTO);
            return ResponseEntity.ok("Usuario creado con exito!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para registrar un administrador");
    }

    // Método auxiliar para verificar el rol del usuario autenticado
    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }
}


