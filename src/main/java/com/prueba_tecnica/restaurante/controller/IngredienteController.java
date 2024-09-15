package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.ingredientes.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity guardarIngrediente(@RequestBody @Valid DatosIngredientesDTO datosIngredientesDTO){
        service.guardarIngrediente(datosIngredientesDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity actualizarIngrediente(@PathVariable Long id, @RequestBody ActualizarIngredienteDTO actualizarIngredienteDTO){
       var datos = service.actualizarIngrediente(id, actualizarIngredienteDTO);
       //retorna los datos actualizados
        return ResponseEntity.ok(datos);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<ListaIngredientesDTO>> listarIngredientes(Pageable pageable){
        var listaIngredientes = service.listarIngredientes(pageable)
                .map(ListaIngredientesDTO::new);

        return ResponseEntity.ok(listaIngredientes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity listarIngredienteId(@PathVariable Long id){
        var listaIngredientes = service.listarIngredientesId(id);

        return ResponseEntity.ok(listaIngredientes);
    }


}
