package com.prueba_tecnica.restaurante.infra.seguridad;

import com.prueba_tecnica.restaurante.infra.errores.Tratar401;
import com.prueba_tecnica.restaurante.infra.errores.Tratar403;
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
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**","/v3/api-docs/**").permitAll()//se da acceso a la documentacion tanto en formato json como en la ui
                .requestMatchers("/restaurante/ingredientes/**").hasRole("ADMINISTRADOR")//se da acceso a los ingredientes solo a los admins
                .requestMatchers("/restaurante/platos/**").hasRole("ADMINISTRADOR")//se da acceso a los platos solo a los admins y a todas las subconsultas
                .requestMatchers("/restaurante/ordenes/**").hasRole("MESERO")//se da acceso a las ordenes solo a los meseros
                .anyRequest().authenticated()//Requerir autenticación para cualquier otra petición
                .and()
                .exceptionHandling()//ermite agregar personalizaciones sobre cómo manejar las excepciones de seguridad
                .authenticationEntryPoint(new Tratar401())//lanzar el 401
                .accessDeniedHandler(new Tratar403())//lanzar 403
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
