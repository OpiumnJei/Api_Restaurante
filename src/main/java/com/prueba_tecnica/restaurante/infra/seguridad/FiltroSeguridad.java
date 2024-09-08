package com.prueba_tecnica.restaurante.infra.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//filtro usado para filtrar las request que se hagan a los solicitudes http de la api
@Component//component es el estereotipo mas usado de spring a la hora de gestionar entidades/clases
public class FiltroSeguridad extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("el filtro esta on fire");
    }
}
