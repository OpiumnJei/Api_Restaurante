package com.prueba_tecnica.restaurante.infra.seguridad;

import com.prueba_tecnica.restaurante.domain.usuarios.UsuarioRepository;
import com.prueba_tecnica.restaurante.infra.autenticacion.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//filtro usado para filtrar las request que se hagan a los solicitudes http de la api
@Component//component es el estereotipo mas usado de spring a la hora de gestionar entidades/clases
public class FiltroSeguridad extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //token enviado atraves del cliente(postman) desde el header el campo de authentication
        var headerToken = request.getHeader("Authorization");

        //verificar que el token enviado no es null
        if(headerToken != null){
            //si el token enviado no es null
            var token =  headerToken.replace("Bearer ", "");//se reemplaza el prefijo bearer por un espacio vacio

            //obtenemos el nombre del usuario atraves del toke
            var nombreUsuario = tokenService.getSubject(token);
            //verificamos que el nombre de usuario no sea nulo
            if(nombreUsuario != null){
                //buscar al usuario en  la base de datos
                var usuarioValido = usuarioRepository.findByNombreUsuario(nombreUsuario);

                //se crea un objeto de autenticación(para realizar una autenticacion forzada)
                var autenticacionUsuario = new UsernamePasswordAuthenticationToken(usuarioValido, null,
                        usuarioValido.getAuthorities());//se pasan los roles del usuario

                //le indicamos a Spring Security que la solicitud está autenticada, para que procese la solicitud
                SecurityContextHolder.getContext().setAuthentication(autenticacionUsuario);
            }
        }
        //método que pasa la solicitud y la respuesta al siguiente filtro en la cadena.
        filterChain.doFilter(request, response);
    }

}

