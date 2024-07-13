package com.biblio.bibliotech.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblio.bibliotech.modelos.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long>{
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByIdioma(String idioma);

    @Query("SELECT l.idioma, COUNT(l) FROM Libro l GROUP BY l.idioma")
    List<Object[]> contarLibrosPorIdioma();
}
