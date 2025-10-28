package com.apiweb.pagina.Configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apiweb.pagina.Entidades.TipoUsuario;
import com.apiweb.pagina.Entidades.Usuario;
import com.apiweb.pagina.Repositorios.TipoUsuarioRepository;
import com.apiweb.pagina.Servicio.UsuarioService;





@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(TipoUsuarioRepository tipoRepo, UsuarioService usuarioService) {
        return args -> {
            if (!tipoRepo.findAll().iterator().hasNext()) {
                TipoUsuario adminTipo = new TipoUsuario();
                adminTipo.setSecuencial(0);
                adminTipo.setNombre("Admin");
                tipoRepo.save(adminTipo);
                 adminTipo = new TipoUsuario();
                adminTipo.setSecuencial(0);
                adminTipo.setNombre("Usuario");
                tipoRepo.save(adminTipo);

                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setTipoUsuario(adminTipo);

                usuarioService.guardar(admin);
            }
        };
    }
}
