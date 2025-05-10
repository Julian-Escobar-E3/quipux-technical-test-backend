package com.example.quipux_technical_test.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String artista;
    private String album;
    private String anno;
    private String genero;

    @ManyToOne
    @JoinColumn(name = "lista_id")
    @JsonIgnore
    private ListaReproduccion lista;

}
