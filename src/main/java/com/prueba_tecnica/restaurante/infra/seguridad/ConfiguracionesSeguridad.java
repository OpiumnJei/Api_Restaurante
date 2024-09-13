package com.prueba_tecnica.restaurante.infra.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.reflect.Method;

@Configuration //spring siempre carga las configuraciopn en primer lugar
@EnableWebSecurity//habilita la configuración de seguridad web personalizada en una aplicación
public class ConfiguracionesSeguridad {

    //inyectamos el filtro de seguridad
    @Autowired
    private FiltroSeguridad filtroSeguridad;

    //configuracion cadena de filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() // Deshabilita CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sesiones stateless (JWT)
                .and()
                .authorizeHttpRequests()//Método para manejar la autorización
                .requestMatchers(HttpMethod.POST, "/restaurante/login").permitAll() //Permitir login sin autenticación
                .anyRequest().authenticated()//Requerir autenticación para cualquier otra petición
                .and()
                .addFilterBefore(filtroSeguridad, UsernamePasswordAuthenticationFilter.class)//agregamos nuestro filtro antes que el de Spring security
                .build();
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
