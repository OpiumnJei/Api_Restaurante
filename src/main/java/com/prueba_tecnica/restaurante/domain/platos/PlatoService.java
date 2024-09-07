package com.prueba_tecnica.restaurante.domain.platos;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;
import com.prueba_tecnica.restaurante.domain.ingredientes.IngredienteRepository;
import com.prueba_tecnica.restaurante.domain.ingredientes.ListaIngredientesDTO;
import com.prueba_tecnica.restaurante.infra.errores.DuplicatedItemsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;

    //guardar un plato
    public void guardarPlato(DatosPlatoDTO datosPlatoDTO){
        //verificar que no existan platos duplicados
        var verificarPlato = platoRepository.existsByNombre(datosPlatoDTO.nombre());
        if(verificarPlato){
            throw new DuplicatedItemsException("Ya hay un registro de "+datosPlatoDTO.nombre());
        }

        //mapear lista ids ingredientes(dto) a una de ingredientes
        List<Ingrediente> ingredientes = datosPlatoDTO.ingredientesIds()
                           .stream()
                             .map(id -> ingredienteRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("Ingrediente con id "+id+" no encontrado")))
                                 .toList();

        //convertir nombres de las categorias a mayusculas para que coincidan con los campos del Enum
       //CategoriaPlato categoriaPlato = CategoriaPlato.valueOf(datosPlatoDTO.categoriaPlato().name().toUpperCase());

        platoRepository.save(new Plato(null, datosPlatoDTO.nombre().toLowerCase(), datosPlatoDTO.precio(), datosPlatoDTO.cantidadPersonas(), ingredientes, datosPlatoDTO.categoriaPlato()));
    }

   //actualizar plato, solo se pueden actualizar los ingredientes (agregar o eliminar)
    public ResponsePlatoDTO actualizarPlato(Long platoID, ActualizarPlatoDTO actualizarPlatoDTO){
        //verificar que el plato exista en la bd
        Plato plato = platoRepository.findById(platoID)
                .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));

        //agregar ingredientes
        if (actualizarPlatoDTO.ingredientesIdsParaAgregar() != null) {
            //ingredienteId sera igual a los datos enviados en la lista de ids enviados por el usuario
            for (Long ingredienteId : actualizarPlatoDTO.ingredientesIdsParaAgregar()) {
                //se valida que el ingrediente exista en la db
                Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId)
                        .orElseThrow(() -> new EntityNotFoundException("Ingrediente con id "+ingredienteId+" no encontrado"));

                //obtiene la lista, y agrega el nuevo ingrediente(id)
                plato.getIngredientes().add(ingrediente);
            }
        }

        //Borrar ingredientes
        if (actualizarPlatoDTO.ingredientesIdsParaBorrar() != null) {
            for (Long ingredienteId : actualizarPlatoDTO.ingredientesIdsParaBorrar()) {
                Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId)
                        .orElseThrow(() -> new EntityNotFoundException("Ingrediente con id "+ingredienteId+" no encontrado"));
                plato.getIngredientes().remove(ingrediente);
            }
        }

        // Guardar los cambios en la base de datos
        Plato platoActualizado = platoRepository.save(plato);

        //datos a retornar al usuario
        return new ResponsePlatoDTO(
                platoActualizado.getId(),
                platoActualizado.getNombre(),
                platoActualizado.getPrecio(),
                platoActualizado.getCantidadPersonas(),
                platoActualizado.getIngredientes()
                        .stream()
                        .map(Ingrediente::getId)
                        .toList(),
                platoActualizado.getCategoriaPlato());
    }

    //listar platos
    public Page<Plato> listarPlatos(Pageable pageable){
        return platoRepository.findAll(pageable);
    }

    //listarPlatos por id
    public ListaPlatosDTO listarplatoId(Long id){
        //se verifica que el plato exista en la db
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plato con id "+id+" no encontrado"));

        //se retorna una lista de ListaPlatosDTO, cuyos datos son enviados desde la entidad Plato(plato.getId.....)
        return new ListaPlatosDTO(plato.getId(), plato.getNombre(), plato.getPrecio(), plato.getCantidadPersonas(),
                plato.getIngredientes()
                        .stream()
                        .map(Ingrediente::getId)
                        .toList(),
                plato.getCategoriaPlato());
    }
}
