package com.communitas.store.app.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(nullable = false,length = 45)
    private String nombre;
    @Column(name = "creado_en",nullable = false)
    private LocalDateTime creadoEn;

    @OneToMany(mappedBy = "categoria",fetch = FetchType.EAGER)
    private List<Libro> libros;

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        for (Libro libro: libros) {
            libro.setCategoria(this);
        }
    }
    @PrePersist
    void initCreatedAt(){
        creadoEn=LocalDateTime.now();
    }
}
