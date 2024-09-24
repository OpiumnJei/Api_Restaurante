package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;
import com.prueba_tecnica.restaurante.domain.platos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Platos", description = "Operaciones relacionadas con la gestion de platos.")
@SecurityRequirement(name = "bearer-key")//se usa para indicar que un endpoint específico requiere autenticación o autorización
@RestController
@RequestMapping("/restaurante/platos")
public class PlatoController {

    @Autowired
    private PlatoService service;

    @Operation(summary = "Registrar Plato", description = "Se crea un registro de un plato.")
    @PostMapping
    public ResponseEntity crearPlato(@RequestBody @Valid DatosPlatoDTO datosPlatoDTO) {
        service.guardarPlato(datosPlatoDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar Plato", description = "Se cambia el estado a una mesa en especifico.")
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePlatoDTO> actualizarPlato(@PathVariable Long id, @RequestBody ActualizarPlatoDTO actualizarPlatoDTO) {
        ResponsePlatoDTO platoActualizado = service.actualizarPlato(id, actualizarPlatoDTO);
        //retornar datos actualizados
        return ResponseEntity.ok(platoActualizado);
    }

    @Operation(summary = "Listar Plato", description = "Se listan todos los registros de platos.")
    @GetMapping
    public ResponseEntity<Page<ListaPlatosDTO>> listarPlatos(Pageable pageable) {

        //Se mapea cada registro de un Plato a una listaPlatosDTO
        return ResponseEntity.ok(service.listarPlatos(pageable).map(ListaPlatosDTO::new)); //se llama al constructor de ListaPlatosDTO
    }

    @Operation(summary = "Listar Plato ID", description = "Se lista un plato en especifico.")
    @GetMapping("/{id}")
    public ResponseEntity listarPlatosId(@PathVariable Long id) {

        var datosPlato = service.listarplatoId(id);

        return ResponseEntity.ok(datosPlato);
    }
    
}
