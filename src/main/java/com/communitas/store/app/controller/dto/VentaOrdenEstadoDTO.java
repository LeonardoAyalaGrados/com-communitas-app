package com.communitas.store.app.controller.dto;

import com.communitas.store.app.entity.VentaOrden;
import lombok.Data;

@Data
public class VentaOrdenEstadoDTO {
    private VentaOrden.Estado estado;
}
