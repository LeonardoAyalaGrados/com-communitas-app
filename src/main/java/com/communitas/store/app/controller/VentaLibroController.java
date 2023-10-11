package com.communitas.store.app.controller;


import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.communitas.store.app.repository.UsuarioRepository;
import com.communitas.store.app.repository.VentaLibroRepository;
import com.communitas.store.app.repository.VentaOrdenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/venta-libro")
public class VentaLibroController {

    @Autowired
    private VentaOrdenRepository ventaOrdenRepository;
    @Autowired
    private VentaLibroRepository libroRepository;

    @GetMapping("/{id}")
    public Iterable<VentaLibro> miDetalleVenta(@PathVariable int id){
        return libroRepository.miDetalleVenta(id);
    }
}
