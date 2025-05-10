package com.example.quipux_technical_test.controllers;

import com.example.quipux_technical_test.models.ListaReproduccion;
import com.example.quipux_technical_test.services.ListaReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lists")
public class ListaReproduccionController{

    @Autowired
    private ListaReproduccionService servicio;

    @PostMapping
    public ResponseEntity<ListaReproduccion> crearLista(@RequestBody ListaReproduccion lista) {
        ListaReproduccion creada = servicio.crearLista(lista);
        String nombreCodificado = URLEncoder.encode(creada.getNombre(), StandardCharsets.UTF_8);
        URI uri = URI.create("/api/v1/lists/" + nombreCodificado);
        return ResponseEntity.created(uri).body(creada);
    }

    @GetMapping
    public List<ListaReproduccion> obtenerTodas() {
        return servicio.obtenerTodas();
    }

    @GetMapping("/{nombre}")
    public ListaReproduccion obtenerUna(@PathVariable String nombre) {
        return servicio.obtenerPorNombre(nombre);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminar(@PathVariable String nombre) {
        servicio.borrarPorNombre(nombre);
        return ResponseEntity.noContent().build();
    }
}
