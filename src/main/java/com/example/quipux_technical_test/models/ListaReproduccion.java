package com.example.quipux_technical_test.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class ListaReproduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cancion> canciones;
}