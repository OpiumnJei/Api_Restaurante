package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.usuarios.LoginUsuarioDTO;
import com.prueba_tecnica.restaurante.domain.usuarios.Usuario;
import com.prueba_tecnica.restaurante.infra.autenticacion.ResponseTokenJWT;
import com.prueba_tecnica.restaurante.infra.autenticacion.TokenService;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/restaurante/login")
public class AutenticacionUsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager; //responsable de la autenticacion

    @Autowired
    private TokenService tokenService; //servicio donde se genera el token jwt

    //autenticarDatos
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid LoginUsuarioDTO loginUsuarioDTO){
        //token representa una solicitud de autenticación con las credenciales proporcionadas.
        Authentication datosUsuario = new UsernamePasswordAuthenticationToken(loginUsuarioDTO.nombreUsuario(), loginUsuarioDTO.contrasenia());
        // Se pasa el token de autenticación al AuthenticationManager para intentar autenticar al usuario.
        var usuarioAutenticado = authenticationManager.authenticate(datosUsuario);

        //retornar el token generado una vez autenticado el usuario
        //Se usa un "casteo" para asegurar que el principal sea tratado como un objeto de la clase Usuario.
        var tokenJwt = tokenService.generarTokenJwt((Usuario) usuarioAutenticado.getPrincipal());
        //retorna jwt y el rol del usuario
        return ResponseEntity.ok(new ResponseTokenJWT(tokenJwt, ((Usuario) usuarioAutenticado.getPrincipal()).getRolUsuario().name()));
    }





}
