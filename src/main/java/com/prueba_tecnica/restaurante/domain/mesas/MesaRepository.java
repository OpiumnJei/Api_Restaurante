package com.prueba_tecnica.restaurante.domain.mesas;

import org.springframework.data.jpa.repository.JpaRepository;
//Mesa es la entidad en la que se trabajara, Long es el dato del identificador (Id) de la entidad
public interface MesaRepository extends JpaRepository<Mesa, Long> {
}
