package com.example.quipux_technical_test.repository;

import com.example.quipux_technical_test.models.ListaReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, Long> {
    Optional<ListaReproduccion> findByNombre(String nombre);
    void deleteByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
