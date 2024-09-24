package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.ordenes.ActualizarOrdenDTO;
import com.prueba_tecnica.restaurante.domain.ordenes.DatosOrdenDTO;
import com.prueba_tecnica.restaurante.domain.ordenes.OrdenService;
import com.prueba_tecnica.restaurante.domain.ordenes.ResponseOrdenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Ordenes", description = "Operaciones relacionadas con la gestion de ordenes.")
@RestController
@RequestMapping("/restaurante/ordenes")
@SecurityRequirement(name = "bearer-key")//se usa para indicar que un endpoint específico requiere autenticación o autorización
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Operation(summary = "Registrar orden", description = "Se crea registro de una orden.")
    @PostMapping
    public ResponseEntity crearOrden(@RequestBody @Valid DatosOrdenDTO datosOrdenDTO){
        ordenService.crearOrden(datosOrdenDTO);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar orden", description = "Solo se pueden agregar o quitar platos a una orden.")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseOrdenDTO> actualizarPlatosOrden(@PathVariable Long id, @RequestBody ActualizarOrdenDTO actualizarOrdenDTO){
        ResponseOrdenDTO ordenActualizada = ordenService.actualizarOrden(id,actualizarOrdenDTO);
        return ResponseEntity.ok(ordenActualizada);
    }

    @Operation(summary = "Listar orden", description = "Se listan todos los registros de ordenes.")
    @GetMapping
    public ResponseEntity<Page<ResponseOrdenDTO>> listarOrdenes(Pageable pageable){
        return ResponseEntity.ok(ordenService.listarOrdenes(pageable).map(ResponseOrdenDTO::new));
    }

    @Operation(summary = "Listar orden ID", description = "Se lista un registro en especifico de una orden.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrdenDTO> listarOrdenId(@PathVariable Long id){
        return ResponseEntity.ok(ordenService.listarOrdenId(id));
    }

    @Operation(summary = "Eliminar orden", description = "Se elimina un registro especifico de una orden.")
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarOrden(@PathVariable Long id){
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }


}
