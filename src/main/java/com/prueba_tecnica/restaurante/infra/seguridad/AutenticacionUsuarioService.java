package com.prueba_tecnica.restaurante.infra.seguridad;

import com.prueba_tecnica.restaurante.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//userDetails es una interfaz propia de Spring que se usa para la autenticacion
@Service
public class AutenticacionUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}
