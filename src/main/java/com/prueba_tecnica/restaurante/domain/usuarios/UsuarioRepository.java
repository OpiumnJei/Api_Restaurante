package com.prueba_tecnica.restaurante.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

//repositorio para usuarios
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
//Una vez encontrado el usuario, el método retorna un objeto que implementa `UserDetails`
    UserDetails findByNombreUsuario(String nombreUsuario);
}
