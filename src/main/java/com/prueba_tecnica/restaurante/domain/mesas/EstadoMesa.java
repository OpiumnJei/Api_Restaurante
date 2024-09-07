package com.prueba_tecnica.restaurante.domain.mesas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum EstadoMesa {
     DISPONIBLE,
    @JsonProperty("en proceso")
    EN_PROCESO_DE_ATENCION,
    ATENDIDA;

     //convertir minusculas a mayusculas en cada entrada que se haga al enum
    @JsonCreator
    public static EstadoMesa fromString(String EstadoUsuario){
         return  EstadoMesa.valueOf(EstadoUsuario.toUpperCase());
    }
}
