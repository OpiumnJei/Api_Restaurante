package com.prueba_tecnica.restaurante.infra.autenticacion;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.prueba_tecnica.restaurante.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//servicio encargado de la generacion y validacion de token
@Service
public class TokenService {

    @Value("${api.security-token.secret}")//extrae el valor de esta propiedad
    private String secretKey;//toma el valor extraido de la propiedad

    //crear token
    public String generarTokenJwt(Usuario usuario) {
        String tokenJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            tokenJWT = JWT.create()
                    .withIssuer("Restaurante") //quien emite el token
                    .withSubject(usuario.getUsername())//a quien va dirido el token
                    .withClaim("role: ", usuario.getRolUsuario().name())//retorna el rol del usuario
                    .withExpiresAt(generarTiempoExpiracion())//tiempo de expiracion del token
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
        return tokenJWT;
    }

    //genera el tiempo de expiracion del token
    private Instant generarTiempoExpiracion() {
        //añade 2 horas al tiempo actual y retorna el resultado como un objeto de tipo Instant
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
    }

    //este metodo valida el token enviado desde el Filtro de Seguridad
    public String getSubject(String headerToken) {
        // Validar que el token no sea nulo
        if (headerToken == null) {
            throw new RuntimeException("token invalido");
        }

        DecodedJWT verifier = null; //se utiliza para representar el resultado de la verificación

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);//algoritmo HMAC con la clave secreta
            //se construye el verificador y validar el token
            verifier = JWT.require(algorithm)
                    .withIssuer("Restaurante")
                    .build()
                    .verify(headerToken);
            verifier.getSubject(); //se obtiene el subject/nombre del usuario a quien fue emitido el token
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }

        //verificar que el subject no sea nulo
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }
        //se retorna el subject
        return verifier.getSubject();

    }
}

