package com.apiweb.pagina.Entidades;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GeneratorType;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Empresa.java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secuencial;
    private String nombre;
    private String logo;
    @Column(columnDefinition = "TEXT")
    private String mision;
    @Column(columnDefinition = "TEXT")
    private String vision;
    private String anio;
    private String realizadopor;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Banner> banners = new ArrayList<>();

    @Override
    public String toString() {
        return "Empresa{" +
                "secuencial=" + secuencial +
                ", nombre='" + nombre + '\'' +
                ", logo='" + logo + '\'' +
                ", mision='" + mision + '\'' +
                ", vision='" + vision + '\'' +
                ", anio='" + anio + '\'' +
                ", realizadopor='" + realizadopor + '\'' +
                ", banners=" + (banners != null ? banners.size() + " banners" : "null") +
                '}';
    }
}
