package com.communitas.store.app.response;

import com.communitas.store.app.entity.Categoria;
import com.communitas.store.app.entity.Libro;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibroResponse {
    private Integer idLibro;
    private String titulo;
    private String descripcion;
    private Float precio;
    private String imagen;
    private int stock;
    private String autor;
    private Libro.Tapa tapa;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private int paginas;
    private int anio;
    private Integer categoria;
    private String nombreCategoria;
}
