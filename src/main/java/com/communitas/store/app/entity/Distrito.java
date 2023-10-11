package com.communitas.store.app.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "distrito")
public class Distrito {
    @Id
    @Column(name = "id_distrito")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDistrito;

    @Column(name = "nombre",length = 45)
    private String nombre;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @OneToMany(mappedBy = "distrito",cascade = CascadeType.ALL)
    private List<Usuario> usuario;

    @PrePersist
    void initcreatedAt(){
        creadoEn=LocalDateTime.now();
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
        for (Usuario u  : usuario) {
            u.setDistrito(this);
        }
    }

}
