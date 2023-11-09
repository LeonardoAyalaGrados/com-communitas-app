package com.communitas.store.app.response;

import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VentaLibroResponse {
    private Integer idVentaLibro;
    private Float precio;
    private int cantidad;
    private LocalDateTime creadoEn;
    private Integer ordenVenta;
    private Libro libro;
}
