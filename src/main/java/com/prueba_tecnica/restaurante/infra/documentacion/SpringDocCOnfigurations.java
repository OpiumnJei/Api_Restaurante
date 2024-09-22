package com.prueba_tecnica.restaurante.infra.documentacion;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//clase que contiene configuracion adicional sobre la documentacion
@Configuration //le indicamos a spring esta es una clase de configuracion
public class SpringDocCOnfigurations {

    //crear bean de springdoc
    @Bean
    public OpenAPI CustomOpenApi() {
        return new OpenAPI()    //retornar configuracion
                .components(new Components()
                        .addSecuritySchemes("bearer-key", //agrega un esquema de seguridad basado en jwt
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Restaurante")//titulo de la documentacion
                        .description("API Rest de un restaurante, que contiene las funcionalidades CRUD tanto de platos, ingredientes, ordenes, etc. " +
                                "\nAsí como el registro de diferentes usuarios y sus respectivos roles, con el fin de hacer una gestion mas dinamica y agil " +
                                "de las tareas relacionadas al restaurante.")
                        .contact(new Contact()//contactos
                                .name("Backend Developer")
                                .email("jerlinsongonzalez@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("")));
    }
}

