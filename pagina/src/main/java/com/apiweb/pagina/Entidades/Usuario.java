package com.apiweb.pagina.Entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private long secuencial;

    private String nombre;
    private String apellido;
    private String telefono;
    private String username;
    private String password;
    private int estaActivo;
    
    @ManyToOne
    private TipoUsuario tipoUsuario;
    
}