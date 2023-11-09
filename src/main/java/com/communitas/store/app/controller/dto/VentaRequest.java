package com.communitas.store.app.controller.dto;


import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.VentaOrden;
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
    private Integer idUsuario;
    private VentaOrden.TipoEntrega tipoDeEntrega;
    private List<Libro> librosSeleccionados;
}
