package com.prueba_tecnica.restaurante.controller;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;
import com.prueba_tecnica.restaurante.domain.platos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante/platos")
public class PlatoController {

    @Autowired
    private PlatoService service;

    @PostMapping
    public ResponseEntity crearPlato(@RequestBody @Valid DatosPlatoDTO datosPlatoDTO){
        service.guardarPlato(datosPlatoDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePlatoDTO> actualizarPlato(@PathVariable Long id, @RequestBody ActualizarPlatoDTO actualizarPlatoDTO){
        ResponsePlatoDTO platoActualizado = service.actualizarPlato(id, actualizarPlatoDTO);
        //retornar datos actualizados
        return ResponseEntity.ok(platoActualizado);
    }

    @GetMapping
   public ResponseEntity<Page<ListaPlatosDTO>> listarPlatos(Pageable pageable){

        //Se mapea cada registro de un Plato a una listaPlatosDTO
        return ResponseEntity.ok(service.listarPlatos(pageable).map(ListaPlatosDTO::new)); //se llama al constructor de ListaPlatosDTO
    }

    @GetMapping("/{id}")
    public ResponseEntity listarPlatosId(@PathVariable Long id){

        var datosPlato = service.listarplatoId(id);

        return ResponseEntity.ok(datosPlato);
    }



}
