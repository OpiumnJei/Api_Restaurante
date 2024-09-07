package com.prueba_tecnica.restaurante.domain.ingredientes;

import com.prueba_tecnica.restaurante.infra.errores.DuplicatedItemsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

    private String IngredienteNotFoundMessage = "Ingrediente no encontrado";

    @Autowired
    private IngredienteRepository ingredienteRepository;

    //guardar ingredientes
    public void guardarIngrediente(DatosIngredientesDTO datosIngredientesDTO){
        //para que no haya ingredientes duplicados
        var verificarIngrediente = ingredienteRepository.existsByNombre(datosIngredientesDTO.nombre());

        if(verificarIngrediente){
            throw new DuplicatedItemsException("Ya hay un registro de "+datosIngredientesDTO.nombre()+" guardado");
        }

        ingredienteRepository.save(new Ingrediente(null, datosIngredientesDTO.nombre().toLowerCase()));
    }

    //actualizar un ingrediente
    public Ingrediente actualizarIngrediente(Long id, ActualizarIngredienteDTO actualizarIngredienteDTO){

        //verificar que el ingrediente exista en la base de datos
         Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(IngredienteNotFoundMessage));

         //actualizar
        if(actualizarIngredienteDTO.nombre() != null){
            //se setea el nombre enviando por el usuario
            ingrediente.setNombre(actualizarIngredienteDTO.nombre());
        }

        return ingredienteRepository.save(ingrediente);
    }

    //Listar ingredientes usando paginacion
    public Page<Ingrediente> listarIngredientes(Pageable pageable){
        return ingredienteRepository.findAll(pageable);
    }

    //listar ingrediente por id
    public ListaIngredientesDTO listarIngredientesId(Long id){
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(IngredienteNotFoundMessage));

        //retornar los datos usando un dto
        return new ListaIngredientesDTO(ingrediente.getId(), ingrediente.getNombre());
    }

}
