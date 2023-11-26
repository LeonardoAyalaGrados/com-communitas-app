package com.communitas.store.app.controller.dto;


import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.VentaOrden;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentaRequest {

    @JsonProperty("idUsuario")
    private Integer idUsuario;
    @JsonProperty("tipoDeEntrega")
    private VentaOrden.TipoEntrega tipoDeEntrega;
    @JsonProperty("librosSeleccionados")
    private List<Libro> librosSeleccionados;
}
