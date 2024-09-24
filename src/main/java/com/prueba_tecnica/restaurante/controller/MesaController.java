package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.mesas.ActualizarMesaDTO;
import com.prueba_tecnica.restaurante.domain.mesas.DatosMesaDTO;
import com.prueba_tecnica.restaurante.domain.mesas.ListaMesasDTO;
import com.prueba_tecnica.restaurante.domain.mesas.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mesas", description = "Operaciones relacionadas con la gestion de mesas.")
@SecurityRequirement(name = "bearer-key")//se usa para indicar que un endpoint específico requiere autenticación o autorización
@RestController
@RequestMapping("/restaurante/mesas")
public class MesaController {

    @Autowired
    private MesaService service;

    @Operation(summary = "Registrar mesa", description = "Se crea registro de una mesa.")
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity guardarMesa(@RequestBody @Valid DatosMesaDTO datosMesaDTO){
        //se guarda en la bd
        service.guardarMesa(datosMesaDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar mesa", description = "Se actualiza registro de una mesa.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity actualizarMesa(@PathVariable Long id, @RequestBody ActualizarMesaDTO actualizarMesaDTO){
        var mesaActualizada = service.actualizarMesa(id, actualizarMesaDTO);

        return ResponseEntity.ok(new ActualizarMesaDTO(mesaActualizada.getId(), mesaActualizada.getCantidadMaxPersonas(), mesaActualizada.getDescripcionMesa(), mesaActualizada.getEstadoMesa()));
    }

    @Operation(summary = "Listar mesas", description = "Se listan todos los registros de mesas.")
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    public ResponseEntity<Page<ListaMesasDTO>> listarMesas(Pageable pageable){

        var listaMesa = service.listarMesas(pageable);

        return ResponseEntity.ok(service.listarMesas(pageable).map(ListaMesasDTO::new));
    }

    @Operation(summary = "Listar mesa ID", description = "Se lista un registro en especifico de una mesa.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    public ResponseEntity listarMesasId(@PathVariable Long id){

        return ResponseEntity.ok(service.listarMesaId(id));
    }

    //listar las ordenes en proceso de para una mesa
    @Operation(summary = "Listar orden en proceso para una mesa", description = "Se lista orden/nes en proceso que tiene una mesa.")
    @GetMapping("/ordenes/{idMesa}")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity listarOrdenMesaId(@PathVariable Long idMesa){
        var ordenesMesa = service.listarOrdenesMesaId(idMesa);

        return ResponseEntity.ok(ordenesMesa);
    }

    //cambiar estado a una mesa
    @Operation(summary = "Cambiar estado mesa", description = "Se cambia el estado a una mesa en especifico.")
    @PutMapping("/estado-mesa/{id}")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity cambiarEstadoMesa(@PathVariable Long id, @RequestBody DatosMesaDTO datosMesaDTO){

        service.cambiarEstadoMesa(id, datosMesaDTO);
        return ResponseEntity.noContent().build();

    }


}
