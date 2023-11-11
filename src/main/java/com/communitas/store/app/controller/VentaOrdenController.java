package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.OrdenConDetalleDTO;
import com.communitas.store.app.controller.dto.VentaOrdenEstadoDTO;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.communitas.store.app.repository.UsuarioRepository;
import com.communitas.store.app.repository.VentaLibroRepository;
import com.communitas.store.app.repository.VentaOrdenRepository;
import com.communitas.store.app.response.VentaLibroResponse;
import com.communitas.store.app.response.VentaOrdenUsuarioResponse;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/venta-orden")
public class VentaOrdenController {

    @Autowired
    private VentaOrdenRepository ventaOrdenRepository;

    @Autowired
    private VentaLibroRepository ventaLibroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper=new ModelMapper();
    private VentaOrdenUsuarioResponse mapVentaOrdenUsuarioResponse(VentaOrden ventaOrden) {
        return modelMapper.map(ventaOrden, VentaOrdenUsuarioResponse.class);
    }

    @GetMapping("/{id}")
    public List<VentaOrden> ordenPorUsuario(@PathVariable int id){
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()){
            throw new EntityNotFoundException();
        }
        return ventaOrdenRepository.misPedidos(id);
    }

    @GetMapping("/list")
    public List<VentaOrden> ventaOrdenList(){
        return ventaOrdenRepository.findAll();
    };

    @GetMapping("/list-user-orders")
    public List<VentaOrdenUsuarioResponse> listVentaOrdenUsuario(){
        List<VentaOrden> ventasOrdenEncontradas=ventaOrdenRepository.findAll();
        List<VentaOrdenUsuarioResponse> ventaOrdenUsuarioResponses=ventasOrdenEncontradas
                .stream().map(this::mapVentaOrdenUsuarioResponse)
                .collect(Collectors.toList());
        return ventaOrdenUsuarioResponses;
    }

    @PutMapping("/estado/{idVentaOrden}")
    public VentaOrden editarEstadoOrden(@PathVariable Integer idVentaOrden, @Validated @RequestBody VentaOrdenEstadoDTO ventaOrdenEstadoDTO){
        Optional<VentaOrden> ventaOrdenEncontrada=Optional.of(ventaOrdenRepository.findById(idVentaOrden).orElseThrow());
        modelMapper.map(ventaOrdenEstadoDTO, ventaOrdenEncontrada.get());
        return ventaOrdenRepository.save(ventaOrdenEncontrada.get());


    }
}
