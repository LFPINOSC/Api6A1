package com.apiweb.pagina.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Banner.java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secuencial;
    private String url;
    private String descripcion;
    private int estaBanner;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonBackReference
    private Empresa empresa;

    @Override
    public String toString() {
        return "Banner{" +
                "secuencial=" + secuencial +
                ", url='" + url + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estaBanner=" + estaBanner +
                ", empresaId=" + (empresa != null ? empresa.getSecuencial() : null) +
                '}';
    }
}
