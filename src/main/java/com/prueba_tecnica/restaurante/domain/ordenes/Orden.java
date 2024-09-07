package com.prueba_tecnica.restaurante.domain.ordenes;

import com.prueba_tecnica.restaurante.domain.mesas.Mesa;
import com.prueba_tecnica.restaurante.domain.platos.Plato;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "ordenes")
@Entity(name = "Orden")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //Muchas mesas pueden tener una misma orden
    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    //Muchas ordenes pueden tener muchos platos
    @ManyToMany
    @JoinTable(
            name = "orden_plato", // Nombre de la tabla pivote
            joinColumns = @JoinColumn(name = "orden_id"), // Clave foránea que apunta al id en la Tabla orden
            inverseJoinColumns = @JoinColumn(name = "plato_id") // Clave foránea que apunta al id en la tabla plato
    )
    private List<Plato> platosSeleccionados;

    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    public Orden(Mesa mesa, List<Plato> platosSeleccionados, BigDecimal subtotal, EstadoOrden estadoOrden) {
        this.mesa = mesa;
        this.platosSeleccionados = platosSeleccionados;
        this.subtotal = subtotal;
        this.estado = estadoOrden;
    }
}
