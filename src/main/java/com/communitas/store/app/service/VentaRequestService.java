package com.communitas.store.app.service;

import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.communitas.store.app.repository.VentaLibroRepository;
import com.communitas.store.app.repository.VentaOrdenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaRequestService {

    @Autowired
    private VentaOrdenRepository ventaOrdenRepository;
    @Autowired
    private VentaLibroRepository ventaLibroRepository;

    @Transactional
    public void registrarVentaConDetalles(VentaOrden ordenDeVenta) {

        ventaOrdenRepository.save(ordenDeVenta);

        // Guarda los detalles de la venta (VentaLibro) en la base de datos
        for (VentaLibro ventaLibro : ordenDeVenta.getVentaLibros()) {
            ventaLibroRepository.save(ventaLibro);
        }
    }
}
