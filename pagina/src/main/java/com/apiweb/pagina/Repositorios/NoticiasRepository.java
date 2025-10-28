package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.Noticia;

public interface NoticiasRepository extends JpaRepository<Noticia, Long>{
    
}
