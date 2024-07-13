package com.biblio.bibliotech.consultas;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.biblio.bibliotech.modelos.Autor;
import com.biblio.bibliotech.modelos.Libro;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsultaAutor {
    private static final String URL_API = "https://gutendex.com/books/";
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public ConsultaAutor() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Autor> listarAutoresVivosEnAño(int año) throws URISyntaxException, IOException, InterruptedException {
        List<Autor> autoresVivos = new ArrayList<>();

        List<Libro> libros = obtenerTodosLosLibros();
        for (Libro libro : libros) {
            Autor autor = libro.getAutor();
            if (autor != null && autor.getAñoNacimiento() <= año && (autor.getAñoFallecimiento() == 0 || autor.getAñoFallecimiento() > año)) {
                autoresVivos.add(autor);
            }
        }

        return autoresVivos.stream().distinct().collect(Collectors.toList());
    }

    private List<Libro> obtenerTodosLosLibros() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL_API))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Convertir la respuesta JSON a una lista de objetos Libro
            Respuestas respuesta = objectMapper.readValue(response.body(), Respuestas.class);
            return respuesta.getResults();
        } else {
            System.out.println("Error al obtener los libros. Codigo de estado: " + response.statusCode());
            return new ArrayList<>();
        }
    }
}