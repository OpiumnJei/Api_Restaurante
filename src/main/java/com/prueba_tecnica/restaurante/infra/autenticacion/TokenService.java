package com.prueba_tecnica.restaurante.infra.autenticacion;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.prueba_tecnica.restaurante.domain.usuarios.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
//servicio encargado de la generacion y validacion de token
@Service
public class TokenService {


    //crear token
    public String generarTokenJwt(Usuario usuario){
        String tokenJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345");
             tokenJWT = JWT.create()
                    .withIssuer("Restaurante") //quien emite el token
                     .withSubject(usuario.getUsername())//a quien va dirido el token
                     .withClaim("role: ", usuario.getRolUsuario().name())//retorna el rol del usuario
                     .withExpiresAt(generarTiempoExpiracion())//tiempo de expiracion del token
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
        return tokenJWT;
    }

    //genera el tiempo de expiracion del token
    private Instant generarTiempoExpiracion () {
        //añade 2 horas al tiempo actual y retorna el resultado como un objeto de tipo Instant
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
    }
}
