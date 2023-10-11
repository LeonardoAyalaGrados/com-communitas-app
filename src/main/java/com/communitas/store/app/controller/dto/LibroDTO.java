package com.communitas.store.app.controller.dto;

import com.communitas.store.app.entity.Categoria;
import com.communitas.store.app.entity.Libro;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LibroDTO {

    @NotNull
    private String titulo;
    @NotNull
    private String descripcion;
    @NotNull
    private Float precio;
    @NotNull
    private String imagen;
    @NotNull
    private int stock;
    @NotNull
    private String autor;
    @NotNull
    private Libro.Tapa tapa;
    @NotNull
    private int paginas;
    @NotNull
    private int anio;
    @NotNull
    private Categoria categoria;
}
