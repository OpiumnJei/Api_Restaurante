package com.prueba_tecnica.restaurante.domain.usuarios;

import com.prueba_tecnica.restaurante.infra.errores.DuplicatedItemsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //crear un usuario

    public void crearUsuario(DatosUsuariosDTO datosUsuariosDTO){

        //si se encuentra un usuario con el mismo nombre en la bd
        if(usuarioRepository.existsByNombreUsuario(datosUsuariosDTO.nombreUsuario())){
            throw new DuplicatedItemsException("Ya hay un usuario con el mismo nombre");

        }
        //se persisten los datos del dto a la entidad
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(datosUsuariosDTO.nombreUsuario());
        usuario.setContrasenia(passwordEncoder.encode(datosUsuariosDTO.contrasenia()));
        usuario.setRolUsuario(datosUsuariosDTO.rolUsuario());
        usuario.setActivo(true);

        //se guardan los datos en la bd
        usuarioRepository.save(usuario);
    }


}
