package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.TipoUsuario;

public interface TipoUsuarioRepository  extends JpaRepository<TipoUsuario,Long>{

    
}