package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.usuarios.DatosUsuariosDTO;
import com.prueba_tecnica.restaurante.domain.usuarios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurante/registro-usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/mesero")
    public ResponseEntity<String> registrarMesero(@RequestBody @Valid DatosUsuariosDTO datosUsuariosDTO, Authentication authentication) {
        // Verifica si el usuario actual es un Super Admin o Administrador
        if (hasRole(authentication, "SUPER_ADMIN") || hasRole(authentication, "ADMIN")) {
            // Validar y crear el usuario Mesero
            if (!"MESERO".equalsIgnoreCase(datosUsuariosDTO.rolUsuario().name())) {
                return ResponseEntity.badRequest().body("El rol debe ser MESERO");
            }

            usuarioService.crearUsuario(datosUsuariosDTO);
            return ResponseEntity.ok("Mesero registrado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para registrar un mesero");
    }

    @PostMapping("/registro/administrador")
    public ResponseEntity<String> registrarAdministrador(@RequestBody @Valid DatosUsuariosDTO datosUsuariosDTO, Authentication authentication) {
        // Verifica si el usuario actual es un Super Admin
        if (hasRole(authentication, "SUPER_ADMIN")) {
            // Validar y crear el usuario Administrador
            if (!"ADMIN".equalsIgnoreCase(datosUsuariosDTO.rolUsuario().name())) {
                return ResponseEntity.badRequest().body("El rol debe ser ADMIN");
            }
            usuarioService.crearUsuario(datosUsuariosDTO);

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para registrar un administrador");
    }

    // Método auxiliar para verificar el rol del usuario autenticado
    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }
}

