package com.prueba_tecnica.restaurante.domain.ordenes;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;
import com.prueba_tecnica.restaurante.domain.mesas.Mesa;
import com.prueba_tecnica.restaurante.domain.mesas.MesaRepository;
import com.prueba_tecnica.restaurante.domain.platos.ActualizarPlatoDTO;
import com.prueba_tecnica.restaurante.domain.platos.ListaPlatosDTO;
import com.prueba_tecnica.restaurante.domain.platos.Plato;
import com.prueba_tecnica.restaurante.domain.platos.PlatoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private PlatoRepository platoRepository;
    @Autowired
    private MesaRepository mesaRepository;

    //crear una orden
    public void crearOrden(DatosOrdenDTO datosOrdenDTO){
        //verificar si la mesa existe mediante el id
        Mesa mesa = mesaRepository.findById(datosOrdenDTO.mesaId())
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada"));

        //mapear los ids de la entidad plato a una lista
        List<Plato> platosSeleccionados = datosOrdenDTO.platosSeleccionados()
                        .stream()
                                .map(id -> platoRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException("Plato con id "+id+" no encontrado"))
                                ).toList();

        //se inicializa en 0, para poder iterarse en el for
        BigDecimal subtotal = BigDecimal.ZERO;

        //se hace una suma iterativa de los precios de los platos
        for (Plato plato : platosSeleccionados) {
            //en cada iteracion se convierten los datos a bigDecimal y se agregan a subtotal (subtotal <-(datos))
            subtotal = subtotal.add(BigDecimal.valueOf(plato.getPrecio())); //se convierte getPrecio(int) a un BigDecimal
        }
                                            //mesa almacena el id introducido por el usuario al principio del metodo
        ordenRepository.save(new Orden(mesa, platosSeleccionados, subtotal, EstadoOrden.EN_PROCESO));
    }

    //actualizar una orden, solo se pueden agregar o quitar platos
    public ResponseOrdenDTO actualizarOrden(Long id, ActualizarOrdenDTO actualizarOrdenDTO){
        //verificar que el plato exista en la bd
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrado"));

        //agregar ingredientes
        if (actualizarOrdenDTO.platosAgregar() != null) {
            //platosId sera igual a los datos enviados en la lista de ids enviados por el usuario
            for (Long platosId : actualizarOrdenDTO.platosAgregar()) {
                //se valida que el plato exista en la db antes de ser agregados
                Plato plato = platoRepository.findById(platosId)
                        .orElseThrow(() -> new EntityNotFoundException("plato con id "+platosId+" no encontrado"));

                //obtiene la lista, y agrega el nuevo plato(id)
                orden.getPlatosSeleccionados().add(plato);
            }
        }

        //Borrar ingredientes
        if (actualizarOrdenDTO.platosEliminar() != null) {
            for (Long platosId : actualizarOrdenDTO.platosEliminar()) {
                Plato plato = platoRepository.findById(platosId)
                        .orElseThrow(() -> new EntityNotFoundException("plato con id "+platosId+" no encontrado"));

                plato.getIngredientes().remove(plato);
            }
        }

        // Guardar los cambios en la base de datos
        Orden ordenActualizada = ordenRepository.save(orden);

        //datos a retornar al usuario
        return new ResponseOrdenDTO(
                ordenActualizada.getId(),
                ordenActualizada.getMesa().getId(),
                ordenActualizada.getPlatosSeleccionados()
                        .stream()
                        .map(Plato::getId)
                        .toList(),
                ordenActualizada.getSubtotal(),
                ordenActualizada.getEstado()
        );
    }

    //listar todas las ordenes
    public Page<Orden> listarOrdenes(Pageable pageable){
         return ordenRepository.findAll(pageable);
    }

    //listar orden por id
    public ResponseOrdenDTO listarOrdenId(Long id){
        //se verifica que la orden exista en la db
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden con id "+id+" no encontrada"));

        //se retorna una lista de ResponseOrdenDTO, cuyos datos son enviados desde la entidad Orden(mesa,.getId.....)
        return new ResponseOrdenDTO(orden.getId(),
                orden.getMesa().getId(),
                orden.getPlatosSeleccionados()
                        .stream()
                        .map(Plato::getId)
                        .toList(),
                orden.getSubtotal(),
                orden.getEstado()
        );
    }

    //eliminar una orden
    public void eliminarOrden(Long id){

        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));

        ordenRepository.delete(orden);
    }

}
