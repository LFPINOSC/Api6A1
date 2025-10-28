package com.apiweb.pagina.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secuencial;
    private String url;
    private int estadoImagen;

    @ManyToOne
    @JoinColumn(name = "noticia_id")
    @JsonBackReference
    private Noticia noticia;
}
