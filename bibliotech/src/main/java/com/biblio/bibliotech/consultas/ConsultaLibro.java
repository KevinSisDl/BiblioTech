package com.biblio.bibliotech.consultas;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.biblio.bibliotech.modelos.Libro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class ConsultaLibro {
    private static final String URL_API = "https://gutendex.com/books/";

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public ConsultaLibro(){
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Libro> listarTodosLosLibros() throws URISyntaxException, IOException, InterruptedException {
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

    public List<Libro> buscarLibroPorTitulo(String titulo) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL_API + "?search=" + titulo))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Convertir la respuesta JSON a una lista de objetos Libro
            try{
            Respuestas respuesta = objectMapper.readValue(response.body(), Respuestas.class);
            return respuesta.getResults();
            } catch (MismatchedInputException e){
                System.out.println("Error al buscar libros por titulo: " + e.getMessage());
                return new ArrayList<>();
            } 
        } else {
        System.out.println("Error al buscar libros por título. Código de estado: " + response.statusCode());
        return new ArrayList<>();
    }
}
}
