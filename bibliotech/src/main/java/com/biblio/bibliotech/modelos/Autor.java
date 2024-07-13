package com.biblio.bibliotech.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int añoNacimiento;
    private int añoFallecimiento;

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

    public Autor(){}

    public Autor(String nombre, int añoNacimiento, int añoFallecimiento){
        this.nombre = nombre;
        this.añoNacimiento = añoNacimiento;
        this.añoFallecimiento = añoFallecimiento;
    }

    @JsonProperty("id")
    @JsonAlias("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    @JsonAlias("nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonProperty("birth_year")
    @JsonAlias("añoNacimiento")
    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    @JsonProperty("death_year")
    @JsonAlias("añoFallecimiento")
    public int getAñoFallecimiento() {
        return añoFallecimiento;
    }

    public void setAñoFallecimiento(int añoFallecimiento) {
        this.añoFallecimiento = añoFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString(){
        return "Autor: " + nombre + ", Año De Nacimiento: "
        + añoNacimiento + ", Año De Fallecimiento: " + añoFallecimiento;
    }
}
