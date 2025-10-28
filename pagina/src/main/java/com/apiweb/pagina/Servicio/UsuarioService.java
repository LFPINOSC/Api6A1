package com.apiweb.pagina.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apiweb.pagina.Entidades.Usuario;
import com.apiweb.pagina.Repositorios.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

   public Usuario guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(usuario.getSecuencial());

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();

            // Comparar contraseña nueva con la actual (encriptada)
            if (!passwordEncoder.matches(usuario.getPassword(), usuarioExistente.getPassword())) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            // Actualizar los demás campos
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setUsername(usuario.getUsername());
            usuarioExistente.setEstaActivo(usuario.getEstaActivo());
            usuarioExistente.setTipoUsuario(usuario.getTipoUsuario());

            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + usuario.getSecuencial());
        }
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> buscarUsuario(String ussername){
        return usuarioRepository.findByUsername(ussername);
    }
    public Optional<Usuario> buscarPorSecuencialUsuario(Long secuencial){
        return usuarioRepository.findById(secuencial);
    }
    public void eliminar(Long secuecuencial){
        usuarioRepository.deleteById(secuecuencial);
    }
}