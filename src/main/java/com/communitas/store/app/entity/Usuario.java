package com.communitas.store.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(nullable = false, length = 45)
    private String apellido;

    @Column(name="full_name",nullable = false, length = 45)
    private String fullName;

    @Column(nullable = false,length = 9)
    private String celular;

    @Column(unique = true,nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 30)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @Column(nullable = false, length = 30)
    private String direccion;


    @ManyToOne(cascade = {CascadeType.ALL},optional = false, fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_distrito", referencedColumnName = "id_distrito", updatable = true)
    private Distrito distrito;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<VentaOrden> ordenes;

    public void setOrdenes(List<VentaOrden> ordenes) {
        this.ordenes = ordenes;
        for (VentaOrden ventaOrden: ordenes) {
            ventaOrden.setUsuario(this);
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
    public enum Rol{
        USUARIO,
        ADMINISTRADOR
    }
}
