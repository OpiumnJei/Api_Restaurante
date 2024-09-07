package com.prueba_tecnica.restaurante.infra.errores;

//trata items duplicados
public class DuplicatedItemsException extends RuntimeException{
    public DuplicatedItemsException(String mensaje) {
        super(mensaje);
    }
}
