package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.NoticiaImagen;

public interface NoticiaImagenRepository extends JpaRepository<NoticiaImagen, Long > {
    
}
