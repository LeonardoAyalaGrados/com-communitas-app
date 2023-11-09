package com.communitas.store.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    @Column(nullable = false, length = 100)
    private String titulo;
    @Column(nullable = false, length = 600)
    private String descripcion;
    @Column(nullable = false)
    private Float precio;

    @Column(nullable = false, length = 255)
    private String imagen;

    @Column(nullable = false)
    private int stock;

    @Column (nullable = false)
    private String autor;
    @Enumerated(EnumType.STRING)
    private Tapa tapa;
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @Column(nullable = false)
    private int paginas;

    @Column (nullable = false)
    private int anio;
/*
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_documento_almacenado",referencedColumnName = "id_documento_almacenado")
    private DocumentoAlmacenado documentoAlmacenado;
*/
    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_categoria",referencedColumnName = "id_categoria")
    private Categoria categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "libro" ,fetch = FetchType.EAGER)
    private List<VentaLibro> ventaLibros;

    public void setVentaLibros(List<VentaLibro> ventaLibros) {
        this.ventaLibros = ventaLibros;
        for (VentaLibro ventaLibro:ventaLibros){
            ventaLibro.setLibro(this);
        }
    }

    @PrePersist
    void iniCreatedAt(){
        creadoEn=LocalDateTime.now();
    }

    @PreUpdate
    void iniUpdateAt(){
        actualizadoEn=LocalDateTime.now();
    }
    public enum Tapa{
        RÚSTICA,
        TAPA_DURA,
        BLANDA,
        GRAPAS
    }
}
