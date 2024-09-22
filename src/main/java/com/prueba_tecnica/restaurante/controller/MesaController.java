package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.mesas.ActualizarMesaDTO;
import com.prueba_tecnica.restaurante.domain.mesas.DatosMesaDTO;
import com.prueba_tecnica.restaurante.domain.mesas.ListaMesasDTO;
import com.prueba_tecnica.restaurante.domain.mesas.MesaService;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity guardarMesa(@RequestBody @Valid DatosMesaDTO datosMesaDTO){
        //se guarda en la bd
        service.guardarMesa(datosMesaDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity actualizarMesa(@PathVariable Long id, @RequestBody ActualizarMesaDTO actualizarMesaDTO){
        var mesaActualizada = service.actualizarMesa(id, actualizarMesaDTO);

        return ResponseEntity.ok(new ActualizarMesaDTO(mesaActualizada.getId(), mesaActualizada.getCantidadMaxPersonas(), mesaActualizada.getDescripcionMesa(), mesaActualizada.getEstadoMesa()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    public ResponseEntity<Page<ListaMesasDTO>> listarMesas(Pageable pageable){

        var listaMesa = service.listarMesas(pageable);

        return ResponseEntity.ok(service.listarMesas(pageable).map(ListaMesasDTO::new));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    public ResponseEntity listarMesasId(@PathVariable Long id){

        return ResponseEntity.ok(service.listarMesaId(id));
    }

    //listar las ordenes en proceso de para una mesa
    @GetMapping("/ordenes/{idMesa}")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity listarOrdenMesaId(@PathVariable Long idMesa){
        var ordenesMesa = service.listarOrdenesMesaId(idMesa);

        return ResponseEntity.ok(ordenesMesa);
    }

    //cambiar estado a una mesa
    @PutMapping("/estado-mesa/{id}")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity cambiarEstadoMesa(@PathVariable Long id, @RequestBody DatosMesaDTO datosMesaDTO){

        service.cambiarEstadoMesa(id, datosMesaDTO);
        return ResponseEntity.noContent().build();

    }


}
