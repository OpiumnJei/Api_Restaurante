package com.prueba_tecnica.restaurante.infra.seguridad;

import com.prueba_tecnica.restaurante.domain.usuarios.RolUsuario;
import com.prueba_tecnica.restaurante.domain.usuarios.Usuario;
import com.prueba_tecnica.restaurante.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//se inicializan los usuarios con los roles de SUPER_ADMIN, ADMIN, MESERO, en caso de que no existan
@Component
public class InicializarRegistroUsuarios implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByRolUsuario(RolUsuario.SUPER_ADMIN)) {  // Si no hay SUPER_ADMIN

            System.out.println("command line inicializado");
            Usuario superAdmin = new Usuario();
            superAdmin.setNombreUsuario("superadmin");
            superAdmin.setContrasenia(passwordEncoder.encode("password123"));
            superAdmin.setRolUsuario(RolUsuario.SUPER_ADMIN);
            superAdmin.setActivo(true);
            usuarioRepository.save(superAdmin);
        }

        if (!usuarioRepository.existsByRolUsuario(RolUsuario.ADMINISTRADOR)) {  // Si no hay ADMIN
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setContrasenia(passwordEncoder.encode("adminpass"));
            admin.setRolUsuario(RolUsuario.ADMINISTRADOR);
            admin.setActivo(true);
            usuarioRepository.save(admin);
        }

        if (!usuarioRepository.existsByRolUsuario(RolUsuario.MESERO)) {  // Si no hay MESERO
            Usuario mesero = new Usuario();
            mesero.setNombreUsuario("mesero");
            mesero.setContrasenia(passwordEncoder.encode("meseropass"));
            mesero.setRolUsuario(RolUsuario.MESERO);
            mesero.setActivo(true);
            usuarioRepository.save(mesero);
        }
    }
}
