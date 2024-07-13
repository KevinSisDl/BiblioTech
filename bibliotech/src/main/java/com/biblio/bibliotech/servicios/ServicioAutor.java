package com.biblio.bibliotech.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblio.bibliotech.modelos.Autor;
import com.biblio.bibliotech.repositorios.AutorRepositorio;

@Service
public class ServicioAutor {
    private final AutorRepositorio autorRepositorio;

    public ServicioAutor(AutorRepositorio autorRepositorio){
        this.autorRepositorio = autorRepositorio;
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        return autorRepositorio.findByNombreContainingIgnoreCase(nombre);
    }
    
    public List<Autor> listarAutoresVivosEnAño(int año){
        return autorRepositorio.listarAutoresVivosEnAño(año);
    }

    public List<Autor> listarAutoresPorAñoNacimiento(int añoNacimiento) {
        return autorRepositorio.findByAñoNacimiento(añoNacimiento);
    }

    public List<Autor> listarAutoresPorAñoFallecimiento(int añoFallecimiento) {
        return autorRepositorio.findByAñoFallecimiento(añoFallecimiento);
    }

    public Autor guardarAutor(Autor autor){
        return autorRepositorio.save(autor);
    }

    public Optional<Autor> buscarPorId(Long id){
        return autorRepositorio.findById(id);
    }

    public List<Autor> listarTodos(){
        return autorRepositorio.findAll();
    }

    public void eliminarAutor(Long id){
        autorRepositorio.deleteById(id);
    }
}
