package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.VentaRequest;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.communitas.store.app.repository.LibroRepository;
import com.communitas.store.app.repository.UsuarioRepository;
import com.communitas.store.app.repository.VentaLibroRepository;
import com.communitas.store.app.service.VentaRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/venta")
public class VentaRequestController {
    @Autowired
    private VentaRequestService ventaService;

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private VentaLibroRepository ventaLibroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/crear")
    public ResponseEntity<String> crearOrdenVenta(@RequestBody VentaRequest ventaRequest) {
        float totalVenta=0;
        try {
            //Obtenemos usuario y libro
            Optional<Usuario> usuarioEncontrado=Optional.of(usuarioRepository.findById(ventaRequest.getIdUsuario()).orElseThrow());
            List<Libro> librosSeleccionados = ventaRequest.getLibrosSeleccionados();

            //Nueva orden de venta
            VentaOrden ordenDeVenta = new VentaOrden();
            ordenDeVenta.setEstado(VentaOrden.Estado.PENDIENTE); // Definimos el estado PENDIENTE por defecto
            ordenDeVenta.setUsuario(usuarioEncontrado.get());

            //Lista de VentaLibro asociados a la orden de venta
            List<VentaLibro> ventaLibros = new ArrayList<>();
            for (Libro libro : librosSeleccionados) {
                VentaLibro ventaLibro = new VentaLibro();
                ventaLibro.setPrecio(libro.getPrecio());
                ventaLibro.setCantidad(1);
                ventaLibro.setLibro(libroRepository.findById(libro.getIdLibro()).orElse(null));
                ventaLibro.setVentaOrden(ordenDeVenta);
                ventaLibros.add(ventaLibro);

                //Obtenemos el total de venta por cada libro en el list
                totalVenta=libro.getPrecio()+totalVenta;
            }

            ordenDeVenta.setTotal(totalVenta);
            ordenDeVenta.setVentaLibros(ventaLibros);

            ventaService.registrarVentaConDetalles(ordenDeVenta);

            return new ResponseEntity<>("Orden de venta creada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden de venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
