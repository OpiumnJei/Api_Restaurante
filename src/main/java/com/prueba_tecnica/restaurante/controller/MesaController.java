package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.mesas.ActualizarMesaDTO;
import com.prueba_tecnica.restaurante.domain.mesas.DatosMesaDTO;
import com.prueba_tecnica.restaurante.domain.mesas.ListaMesasDTO;
import com.prueba_tecnica.restaurante.domain.mesas.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante/mesas")
public class MesaController {

    @Autowired
    private MesaService service;

    @PostMapping
    public ResponseEntity guardarMesa(@RequestBody @Valid DatosMesaDTO datosMesaDTO){
        //se guarda en la bd
        service.guardarMesa(datosMesaDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity actualizarMesa(@PathVariable Long id, @RequestBody ActualizarMesaDTO actualizarMesaDTO){
        var mesaActualizada = service.actualizarMesa(id, actualizarMesaDTO);

        return ResponseEntity.ok(new ActualizarMesaDTO(mesaActualizada.getId(), mesaActualizada.getCantidadMaxPersonas(), mesaActualizada.getDescripcionMesa(), mesaActualizada.getEstadoMesa()));
    }

    @GetMapping
    public ResponseEntity<Page<ListaMesasDTO>> listarMesas(Pageable pageable){

        var listaMesa = service.listarMesas(pageable);

        return ResponseEntity.ok(service.listarMesas(pageable).map(ListaMesasDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity listarMesasId(@PathVariable Long id){

        return ResponseEntity.ok(service.listarMesaId(id));
    }

    //listar las ordenes en proceso de para una mesa
    @GetMapping("/ordenes/{idMesa}")
    public ResponseEntity listarOrdenMesaId(@PathVariable Long idMesa){
        var ordenesMesa = service.listarOrdenesMesaId(idMesa);

        return ResponseEntity.ok(ordenesMesa);
    }

    //cambiar estado a una mesa
    @PutMapping("/estado-mesa/{id}")
    public ResponseEntity cambiarEstadoMesa(@PathVariable Long id, @RequestBody DatosMesaDTO datosMesaDTO){

        service.cambiarEstadoMesa(id, datosMesaDTO);
        return ResponseEntity.noContent().build();

    }


}
