package com.prueba_tecnica.restaurante.domain.mesas;

import com.prueba_tecnica.restaurante.domain.ordenes.EstadoOrden;
import com.prueba_tecnica.restaurante.domain.ordenes.Orden;
import com.prueba_tecnica.restaurante.domain.ordenes.OrdenRepository;
import com.prueba_tecnica.restaurante.domain.platos.DatosPlatoDTO;
import com.prueba_tecnica.restaurante.infra.errores.DuplicatedItemsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private OrdenRepository ordenRepository;

    private String mesaNotFoundMessage = "Mesa no encontrada";

    //registrar una mesa
    public void guardarMesa(DatosMesaDTO datosMesaDTO){

        //se mapean los datos del dto a la entidad
        mesaRepository.save(new Mesa(null, datosMesaDTO.cantidadMaxPersonas(),EstadoMesa.DISPONIBLE, datosMesaDTO.descripcionMesa()));
    }

    //actualizar una mesa
    public Mesa actualizarMesa(Long id, ActualizarMesaDTO actualizarMesaDTO){
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(mesaNotFoundMessage));

        if (actualizarMesaDTO.cantidadMaxPersonas() != null){
            mesa.setCantidadMaxPersonas(actualizarMesaDTO.cantidadMaxPersonas());
        }
        if(actualizarMesaDTO.descripcionMesa() != null){
            mesa.setDescripcionMesa(actualizarMesaDTO.descripcionMesa());
        }

        return mesaRepository.save(mesa);
    }

    //listar todas las mesas usando paginacion
    public Page<Mesa> listarMesas(Pageable pageable){
        return mesaRepository.findAll(pageable);
    }

    //listar mesa por id
    public ListaMesasDTO listarMesaId(Long id){
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(mesaNotFoundMessage));

        //se retornan los datos de la mesa mapeados a una lista DTO
        return new ListaMesasDTO(mesa.getId(), mesa.getCantidadMaxPersonas(),
                mesa.getDescripcionMesa(), mesa.getEstadoMesa());
    }

    //listar las ordenes que contiene una mesa
    public List<Orden> listarOrdenesMesaId(Long idMesa){
        List<Orden> ordenes = ordenRepository.findByMesaIdAndEstado(idMesa, EstadoOrden.EN_PROCESO);

        //se comprueba si la mesa no tiene ordenes en procesoe
        if(ordenes.isEmpty()){
            throw new EntityNotFoundException("Esta mesa no tiene ordenes");
        }

        return ordenes;
    }

    //cambiar estado a una mesa
    public void cambiarEstadoMesa(Long id,DatosMesaDTO datosMesaDTO){
        //se verifica si la mesa existe en la base de datos
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(mesaNotFoundMessage));

        if(datosMesaDTO.estadoMesa() != null){
            mesa.cambiarEstadoMesa(datosMesaDTO.estadoMesa());
        }
        else{
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        mesaRepository.save(mesa);
    }


}
