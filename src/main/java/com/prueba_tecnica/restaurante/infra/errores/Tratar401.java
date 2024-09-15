package com.prueba_tecnica.restaurante.infra.errores;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
//tratar error 401, cuando un usuario no esta autenticado
public class Tratar401 implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //se retorna un estado 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //mensaje a enviarse al usuario
        response.getOutputStream().println("No estas autenticado, por favor inicia sesion.");
    }
}
