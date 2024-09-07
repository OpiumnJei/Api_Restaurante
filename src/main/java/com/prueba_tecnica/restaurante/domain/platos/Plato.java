package com.prueba_tecnica.restaurante.domain.platos;

import com.prueba_tecnica.restaurante.domain.ingredientes.Ingrediente;
import com.prueba_tecnica.restaurante.domain.ordenes.Orden;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "platos")
@Entity(name = "Plato")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int precio;

    public Plato(Long id, String nombre, int precio, int cantidadPersonas, List<Ingrediente> ingredientes, CategoriaPlato categoriaPlato) {
        Id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadPersonas = cantidadPersonas;
        this.ingredientes = ingredientes;
        this.categoriaPlato = categoriaPlato;
    }

    @Column(name = "cantidad_personas")
    private int cantidadPersonas;
    @ManyToMany
    @JoinTable(
            name = "plato_ingredientes",
            joinColumns = @JoinColumn(name = "plato_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientes;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_plato")
    private CategoriaPlato categoriaPlato;

    //muchos platos pueden estar en muchas ordenes
    @ManyToMany(mappedBy = "platosSeleccionados")//platosSeleccionados es un campo en la entidad Orden
    private List<Orden> orden;
}
