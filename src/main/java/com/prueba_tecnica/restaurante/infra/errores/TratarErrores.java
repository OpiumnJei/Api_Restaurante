package com.prueba_tecnica.restaurante.infra.errores;

import com.prueba_tecnica.restaurante.domain.mesas.Mesa;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

///se usa para capturar excepciones personalizadas y enviar los Http Status code correctos
@RestControllerAdvice
public class TratarErrores {

    //tratar el error 409, items duplicados
    @ExceptionHandler(DuplicatedItemsException.class)
    public ResponseEntity tratarError409(Exception e) {
        //se escoge el status code, y se pasa el mensaje colocado en la exception
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    //error 400 quiere decir que hay un error en el lado del cliente a la hora de llenar los campos
    //se capturan dos excepciones
    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity tratarError400(Exception e) {

        //si se lanza una exception del tipo MethodArgumentNotValidException
        if (e instanceof MethodArgumentNotValidException) {
            //casteo del tipo e (Exception) aun tipo ex (MethodArgumentNotValidException)
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e; //Downcasting
            var errores = ex.getFieldErrors()
                    .stream()
                    .map(DatosErrorValidacion::new).toList();

            return ResponseEntity.badRequest().body(errores);//error 400
        }
        //si se lanza una exception del tipo IllegalArgumentException
        else if (e instanceof IllegalArgumentException) {//error 500
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error ocurrido en el servidor");
        }
    }

    //tratar el error 404, entidad no encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    //record para mapear errores, usado para tratar el error 400
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
