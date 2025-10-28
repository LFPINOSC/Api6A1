package com.apiweb.pagina.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiweb.pagina.Entidades.Usuario;
import com.apiweb.pagina.Servicio.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario){
        return usuarioService.guardar(usuario);
    }
     @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setSecuencial(id); // Asegurarse de establecer el ID
        return usuarioService.actualizarUsuario(usuario);
    }
    @GetMapping 
    public List<Usuario> listar(){
        return usuarioService.listar();
    }
     @GetMapping("/{username}")
     public Optional<Usuario> listarPorNombro(String username){
        return usuarioService.buscarUsuario(username);
     }
     @GetMapping ("/{secuencial}")
     public Optional<Usuario> listarPorSecuencial(Long secuencial){
        return usuarioService.buscarPorSecuencialUsuario(secuencial);
     }
     @DeleteMapping("/{secuencial}")
     public void eliminar(Long secuencial){
        usuarioService.eliminar(secuencial);
     }
    
    
}