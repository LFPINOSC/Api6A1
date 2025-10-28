package com.apiweb.pagina.Servicios;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.apiweb.pagina.Entidades.TipoUsuario;
import com.apiweb.pagina.Entidades.Usuario;
import com.apiweb.pagina.Repositorios.UsuarioRepository;
import com.apiweb.pagina.Servicio.UsuarioService;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioUsuarioTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock 
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarUsuario(){
        Usuario usuario=new Usuario();
        usuario.setNombre("luis");
        usuario.setApellido("pinos");
        usuario.setTelefono("999999");
        usuario.setUsername("lfpinosc");
        usuario.setPassword("123456");
        usuario.setTipoUsuario(new TipoUsuario(1L,"admin"));

        when(passwordEncoder.encode("123456")).
        thenReturn("claveCifrada");

        when(usuarioRepository.save(any(Usuario.class))).
        thenReturn(usuario);

        Usuario result=usuarioService.guardar(usuario);

        assertEquals("luis",result.getNombre());
        assertEquals("pinos", result.getApellido());
    }
    @Test
    void testEliminarUsuario(){
        Long id=1L;
        usuarioService.eliminar(id);
        verify(usuarioRepository,times(1))
        .deleteById(id);
    }
}
