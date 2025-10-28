package com.apiweb.pagina.Entidades;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Noticia {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secuencial;

    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private int estadoNoticia;
    
   @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NoticiaImagen> noticiaImagens;
    
}
