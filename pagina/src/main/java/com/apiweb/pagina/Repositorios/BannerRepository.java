package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.Banner;

public interface BannerRepository extends JpaRepository<Banner,Long>{
    
}
