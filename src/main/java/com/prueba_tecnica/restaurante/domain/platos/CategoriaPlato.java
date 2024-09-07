package com.prueba_tecnica.restaurante.domain.platos;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CategoriaPlato {
    ENTRADA,
    PLATO_FUERTE,
    POSTRE,
    BEBIDA;

    //convertir string de minusculas a mayusculas, para que coincidan con las constantes(valores) del enum
    @JsonCreator
    public static CategoriaPlato fromString(String CategoriaUsuario) {
        //valueof, retorna un valor del enum que coincida con la categoria enviada por el usuario en el body
        return CategoriaPlato.valueOf(CategoriaUsuario.toUpperCase());
    }
}
