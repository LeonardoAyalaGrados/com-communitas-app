package com.communitas.store.app.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "venta_libro")
public class VentaLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta_libro")
    private Integer idVentaLibro;

    @Column(nullable = false)
    private Float precio;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false,name = "creado_en")
    private LocalDateTime creadoEn;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_libro", referencedColumnName = "id_libro")
    private Libro libro;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_venta_orden", referencedColumnName = "id_venta_orden")
    private VentaOrden ventaOrden;

    @PrePersist
    void initCreatedAt(){
        creadoEn=LocalDateTime.now();
    }

}
