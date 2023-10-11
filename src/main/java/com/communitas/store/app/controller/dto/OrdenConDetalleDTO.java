package com.communitas.store.app.controller.dto;

import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrdenConDetalleDTO {
    private VentaOrden ventaOrden;
    private Iterable<VentaLibro> ventaLibroList;

    public OrdenConDetalleDTO() {
        this.ventaOrden=new VentaOrden();
        this.ventaLibroList=new ArrayList<>();
    }

    public OrdenConDetalleDTO(VentaOrden ventaOrden, Iterable<VentaLibro> ventaLibroList) {
        this.ventaOrden = ventaOrden;
        this.ventaLibroList = ventaLibroList;
    }

}
