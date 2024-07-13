package com.biblio.bibliotech;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.biblio.bibliotech.consultas.ConsultaLibro;
import com.biblio.bibliotech.modelos.Autor;
import com.biblio.bibliotech.modelos.Libro;
import com.biblio.bibliotech.servicios.ServicioAutor;
import com.biblio.bibliotech.servicios.ServicioLibro;

@SpringBootApplication
@EnableJpaRepositories("com.biblio.bibliotech.repositorios")
public class Principal implements CommandLineRunner {
    private final ServicioLibro servicioLibro;
    private final ServicioAutor servicioAutor;
    private final Scanner scanner;

    public Principal(ServicioLibro servicioLibro, ServicioAutor servicioAutor) {
        this.servicioLibro = servicioLibro;
        this.servicioAutor = servicioAutor;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        SpringApplication.run(Principal.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mostrarMenu();
    }

    private void mostrarMenu() throws IOException, InterruptedException, URISyntaxException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Buscar autores por nombre");
            System.out.println("3. Listar todos los libros");
            System.out.println("4. Listar autores registrados");
            System.out.println("5. Listar autores vivos en un año específico");
            System.out.println("6. Listar autores por año de nacimiento");
            System.out.println("7. Listar autores por año de fallecimiento");
            System.out.println("8. Mostrar estadísticas de libros por idioma");
            System.out.println("9. Mostrar top 10 libros más descargados");
            System.out.println("10. Salir");
            System.out.print("Ingrese una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después del entero

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el título del libro a buscar: ");
                    String titulo = scanner.nextLine();
                    buscarLibroPorTitulo(titulo);
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del autor a buscar: ");
                    String nombreAutor = scanner.nextLine();
                    buscarAutoresPorNombre(nombreAutor);
                    break;
                case 3:
                    listarTodosLosLibros();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    System.out.println("Ingrese el año para listar autores vivos: ");
                    int año = scanner.nextInt();
                    scanner.nextLine();
                    listarAutoresVivosEnAño(año);
                    break;
                case 6:
                    System.out.println("Ingrese el año de nacimiento para listar autores: ");
                    int añoNacimiento = scanner.nextInt();
                    scanner.nextLine();
                    listarAutoresPorAñoNacimiento(añoNacimiento);
                    break;
                case 7:
                    System.out.println("Ingrese el año de fallecimiento para listar autores: ");
                    int añoFallecimiento = scanner.nextInt();
                    scanner.nextLine();
                    listarAutoresPorAñoFallecimiento(añoFallecimiento);
                    break;
                case 8:
                    mostrarEstadisticasLibrosPorIdioma();
                    break;
                case 9:
                    mostrarTop10LibrosMasDescargados();
                    break;
                case 10:
                    salir = true;
                    break;
				default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        System.out.println("¡Finalizando programaa!");
    }

    private void buscarLibroPorTitulo(String titulo) throws InterruptedException, URISyntaxException, IOException {
        List<Libro> librosEncontrados = servicioLibro.buscarPorTitulo(titulo);

        if (!librosEncontrados.isEmpty()) {
            System.out.println("\n--- Libros encontrados con el título '" + titulo + "' ---");
            for (Libro libro : librosEncontrados) {
                // Guardar el libro en la base de datos
                servicioLibro.guardarLibro(libro);
                System.out.println(libro);
            }
        } else {
            System.out.println("No se encontraron libros con el título '" + titulo + "'.");
        }
    }

    private void buscarAutoresPorNombre(String nombre) {
        List<Autor> autores = servicioAutor.buscarAutoresPorNombre(nombre);
        if (!autores.isEmpty()) {
            System.out.println("\n--- Autores encontrados con el nombre '" + nombre + "' ---");
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores con el nombre '" + nombre + "'.");
        }
    }

    private void listarTodosLosLibros() throws IOException, InterruptedException, URISyntaxException {
        List<Libro> libros = servicioLibro.listarTodosLosLibros();
        if (!libros.isEmpty()) {
            System.out.println("\n--- Listado de Libros ---");
            libros.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros.");
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = servicioAutor.listarAutoresRegistrados();
        if (!autores.isEmpty()) {
            System.out.println("\n--- Lista de Autores Registrados ---");
            autores.forEach(System.out::println);
        } else {
            System.out.println("No hay autores registrados.");
        }
    }

    private void listarAutoresVivosEnAño(int año) {
        List<Autor> autores = servicioAutor.listarAutoresVivosEnAño(año);
        if (!autores.isEmpty()) {
            System.out.println("\n--- Autores vivos en el año " + año + " ---");
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores vivos en el año " + año + ".");
        }
    }

    private void listarAutoresPorAñoNacimiento(int añoNacimiento) {
        List<Autor> autores = servicioAutor.listarAutoresPorAñoNacimiento(añoNacimiento);
        if (!autores.isEmpty()) {
            System.out.println("\n--- Autores nacidos en el año " + añoNacimiento + " ---");
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores nacidos en el año " + añoNacimiento + ".");
        }
    }

    private void listarAutoresPorAñoFallecimiento(int añoFallecimiento) {
        List<Autor> autores = servicioAutor.listarAutoresPorAñoFallecimiento(añoFallecimiento);
        if (!autores.isEmpty()) {
            System.out.println("\n--- Autores fallecidos en el año " + añoFallecimiento + " ---");
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores fallecidos en el año " + añoFallecimiento + ".");
        }
    }

    private void mostrarEstadisticasLibrosPorIdioma() {
        Map<String, Long> estadisticasPorIdioma = servicioLibro.obtenerEstadisticasPorIdioma();
        if (!estadisticasPorIdioma.isEmpty()) {
            System.out.println("\n--- Estadísticas de Libros por Idioma ---");
            estadisticasPorIdioma.forEach((idioma, cantidad) -> System.out.println(idioma + ": " + cantidad));
        } else {
            System.out.println("No se encontraron estadísticas de libros por idioma.");
        }
    }

    private void mostrarTop10LibrosMasDescargados() {
        List<Libro> libros = servicioLibro.listarTodosLosLibros();
        libros.stream()
            .sorted((l1, l2) -> Integer.compare(l2.getDescargas(), l1.getDescargas())) // Ordenar por descargas (descendente)
            .limit(10) // Limitar a los 10 primeros
            .forEach(System.out::println); // Mostrar los libros
    }
}