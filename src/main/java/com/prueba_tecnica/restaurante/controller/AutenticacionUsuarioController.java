package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.usuarios.LoginUsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurante/login")
public class AutenticacionUsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager; //responsable de la autenticacion
    //autenticarDatos
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid LoginUsuarioDTO loginUsuarioDTO){
        //token representa una solicitud de autenticación con las credenciales proporcionadas.
        Authentication token = new UsernamePasswordAuthenticationToken(loginUsuarioDTO.nombreUsuario(), loginUsuarioDTO.contrasenia());
        // Se pasa el token de autenticación al AuthenticationManager para intentar autenticar al usuario.
        authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }

}
