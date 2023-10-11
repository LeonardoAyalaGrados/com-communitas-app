package com.communitas.store.app.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "venta_orden")
public class VentaOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta_orden")
    private Integer idVentaOrden;

    @Column(nullable = false)
    private Float total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;


    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER,optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_usuario",referencedColumnName = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "ventaOrden" ,fetch = FetchType.EAGER)
    private List<VentaLibro> ventaLibros;

    public void setVentaLibros(List<VentaLibro> ventaLibros) {
        this.ventaLibros = ventaLibros;
        for (VentaLibro ventaLibro: ventaLibros) {
            ventaLibro.setVentaOrden(this);
        }
    }

    @PrePersist
    void initCreatedAt(){
        creadoEn=LocalDateTime.now();
    }


    public enum Estado{
        PENDIENTE,
        DESPACHADO,
    }
}
