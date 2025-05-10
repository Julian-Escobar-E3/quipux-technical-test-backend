package com.example.quipux_technical_test.services;

import com.example.quipux_technical_test.models.ListaReproduccion;
import com.example.quipux_technical_test.repository.ListaReproduccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ListaReproduccionService {

    @Autowired
    private ListaReproduccionRepository repo;
    @Transactional
    public ListaReproduccion crearLista(ListaReproduccion lista) {
        if (lista.getNombre() == null || lista.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la lista no puede ser nulo");
        }
        if (repo.existsByNombre(lista.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una lista con ese nombre");
        }

        //Relación Bideireccional
        if (lista.getCanciones() != null) {
            lista.getCanciones().forEach(cancion -> {
                cancion.setLista(lista);
            });
        }

        return repo.save(lista);
    }

    public List<ListaReproduccion> obtenerTodas() {
        return repo.findAll();
    }

    public ListaReproduccion obtenerPorNombre(String nombre) {
        return repo.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no encontrada"));
    }
    @Transactional
    public void borrarPorNombre(String nombre) {
        ListaReproduccion lista = repo.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no encontrada"));
        repo.delete(lista);
    }
}
