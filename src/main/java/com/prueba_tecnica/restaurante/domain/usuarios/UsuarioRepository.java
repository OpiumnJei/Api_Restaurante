package com.prueba_tecnica.restaurante.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

//repositorio para usuarios
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
