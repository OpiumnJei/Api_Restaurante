package com.prueba_tecnica.restaurante.infra.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //spring siempre carga las configuraciopn en primer lugar
@EnableWebSecurity//habilita la configuración de seguridad web personalizada en una aplicación
public class ConfiguracionesSeguridad {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()//desactivar proteccion csrf(Cross-Site Request Forgery)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//se configura la poliza de sesion como stateless
                .and().build();
    }

    //metodo para que el AuthenticationManager, se usa @Bean para que spring la gestione
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //metodo para generar la encriptacion usando Bcrypt
    @Bean
    public PasswordEncoder encriptarPassword(){
        return new BCryptPasswordEncoder();
    }
}
