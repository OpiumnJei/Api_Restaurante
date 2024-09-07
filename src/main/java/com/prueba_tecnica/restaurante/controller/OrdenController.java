package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.ordenes.ActualizarOrdenDTO;
import com.prueba_tecnica.restaurante.domain.ordenes.DatosOrdenDTO;
import com.prueba_tecnica.restaurante.domain.ordenes.OrdenService;
import com.prueba_tecnica.restaurante.domain.ordenes.ResponseOrdenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @PostMapping
    public ResponseEntity crearOrden(@RequestBody @Valid DatosOrdenDTO datosOrdenDTO){
        ordenService.crearOrden(datosOrdenDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseOrdenDTO> actualizarPlatosOrden(@PathVariable Long id, @RequestBody ActualizarOrdenDTO actualizarOrdenDTO){
        ResponseOrdenDTO ordenActualizada = ordenService.actualizarOrden(id,actualizarOrdenDTO);
        return ResponseEntity.ok(ordenActualizada);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseOrdenDTO>> listarOrdenes(Pageable pageable){
        return ResponseEntity.ok(ordenService.listarOrdenes(pageable).map(ResponseOrdenDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrdenDTO> listarOrdenId(@PathVariable Long id){
        return ResponseEntity.ok(ordenService.listarOrdenId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarOrden(@PathVariable Long id){
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }


}
