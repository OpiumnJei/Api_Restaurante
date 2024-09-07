package com.prueba_tecnica.restaurante.domain.mesas;

import com.prueba_tecnica.restaurante.domain.ordenes.Orden;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="mesas")
@Entity(name = "Mesa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cantidad_max_personas")
    private int cantidadMaxPersonas;
    @Column(name = "descripcion_mesa")
    private String descripcionMesa;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mesa")
    private EstadoMesa estadoMesa;

    //lista de ordenes que posee una mesa
    @OneToMany(mappedBy = "mesa") //"mesa" se refiere al campo mesa en la entidad Orden
    private List<Orden> ordenes;

    public Mesa(Long id, int cantidadMaxPersonas, EstadoMesa estadoMesa, String descripcionMesa) {
        this.id = id;
        this.cantidadMaxPersonas = cantidadMaxPersonas;
        this.estadoMesa = estadoMesa;
        this.descripcionMesa = descripcionMesa;
    }

    //cambiar estado a una mesa
    public void cambiarEstadoMesa(EstadoMesa estadoMesa){
        this.estadoMesa = estadoMesa;
    }
}
