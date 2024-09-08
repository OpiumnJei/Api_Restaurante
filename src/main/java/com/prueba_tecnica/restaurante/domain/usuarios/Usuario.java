package com.prueba_tecnica.restaurante.domain.usuarios;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Table(name="usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
/*
* UserDetails es una interfaz de spring security
* con UserDetails adaptamos nuestra entidad para integrarse con el
* sistema de autenticacion de spring security
* */

public class Usuario implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //retorna una lista con el/los roles/autoridades que tiene un usuario
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rolUsuario.name()));
    }

    @Override
    public String getPassword() {
        return contrasenia;//obtiene la contrasenia del usuario
    }

    @Override
    public String getUsername() {
        return nombreUsuario;//obtiene el nombre del usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//la cuenta no ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//la cuenta no esta bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//las credenciales no han expirado
    }

    @Override
    public boolean isEnabled() {
        return true; //esta habilitado
    }

    //commmit desde intelli j
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    private String contrasenia;
    private Boolean activo;


    @Enumerated(EnumType.STRING)
    @Column(name = "rol_usuario")
    private RolUsuario rolUsuario;



}
