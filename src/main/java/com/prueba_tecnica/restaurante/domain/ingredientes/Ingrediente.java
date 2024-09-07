package com.prueba_tecnica.restaurante.domain.ingredientes;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "ingredientes")
@Entity(name = "Ingrediente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;


}
