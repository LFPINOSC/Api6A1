package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa,Long>{
    
}
