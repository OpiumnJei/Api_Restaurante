package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.ingredientes.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Ingredientes", description = "Operaciones relacionadas con la gestion de ingredientes.")
@SecurityRequirement(name = "bearer-key")//se usa para indicar que un endpoint específico requiere autenticación o autorización
@RestController
@RequestMapping("/restaurante/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService service;

    @PostMapping
    public ResponseEntity guardarIngrediente(@RequestBody @Valid DatosIngredientesDTO datosIngredientesDTO){
        service.guardarIngrediente(datosIngredientesDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity actualizarIngrediente(@PathVariable Long id, @RequestBody ActualizarIngredienteDTO actualizarIngredienteDTO){
       var datos = service.actualizarIngrediente(id, actualizarIngredienteDTO);
       //retorna los datos actualizados
        return ResponseEntity.ok(datos);
    }

    @GetMapping
    public ResponseEntity<Page<ListaIngredientesDTO>> listarIngredientes(Pageable pageable){
        var listaIngredientes = service.listarIngredientes(pageable)
                .map(ListaIngredientesDTO::new);

        return ResponseEntity.ok(listaIngredientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity listarIngredienteId(@PathVariable Long id){
        var listaIngredientes = service.listarIngredientesId(id);

        return ResponseEntity.ok(listaIngredientes);
    }


}
