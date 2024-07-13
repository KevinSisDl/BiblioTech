package com.biblio.bibliotech.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblio.bibliotech.modelos.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long>{
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    List<Autor> findByAñoNacimiento(int añoNacimiento);
    List<Autor> findByAñoFallecimiento(int añoFallecimiento);
    
    @Query("SELECT a FROM Autor a WHERE a.añoFallecimiento IS NULL OR a.añoFallecimiento > :año")
    List<Autor> listarAutoresVivosEnAño(int año);
}
