package com.biblio.bibliotech.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.biblio.bibliotech.modelos.Libro;
import com.biblio.bibliotech.repositorios.LibroRepositorio;

@Service
public class ServicioLibro {
    @Autowired
    private final LibroRepositorio libroRepositorio;

    public ServicioLibro(LibroRepositorio libroRepositorio){
        this.libroRepositorio = libroRepositorio;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepositorio.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Libro> listarTodosLosLibros(){
        return libroRepositorio.findAll();
    }

    public List<Libro> listarPorIdioma(String idioma) {
        return libroRepositorio.findByIdioma(idioma);
    }

    public Libro guardarLibro(Libro libro){
        return libroRepositorio.save(libro);
    }

    public Optional<Libro> buscarPorId(Long id){
        return libroRepositorio.findById(id);
    }

    public void eliminarLibro(Long id){
        libroRepositorio.deleteById(id);
    }

    public Map<String, Long> obtenerEstadisticasPorIdioma(){
        List<Object[]> results = libroRepositorio.contarLibrosPorIdioma();

        return results.stream()
        .collect(Collectors.toMap(
                arr -> (String) arr[0],     // Idioma
                arr -> (Long) arr[1]        // Cantidad de libros
        ));
    }
    public DoubleSummaryStatistics obtenerEstadisticasPorDescargas() {
        List<Libro> libros = libroRepositorio.findAll();
        return libros.stream()
                .mapToDouble(Libro::getDescargas)
                .summaryStatistics();
    }
    public List<Libro> obtenerTop10LibrosMasDescargados() {
        return libroRepositorio.findAll().stream()
                .sorted((libro1, libro2) -> Integer.compare(libro2.getDescargas(), libro1.getDescargas()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
