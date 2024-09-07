package com.prueba_tecnica.restaurante.domain.usuarios;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name="usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    //commmit desde intelli j
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;
    private String contrasenia;
    private Boolean activo;

    @Enumerated(EnumType.STRING)
    private RolUsuario rolUsuario;
}
