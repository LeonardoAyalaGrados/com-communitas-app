package com.communitas.store.app.controller;


import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.communitas.store.app.repository.UsuarioRepository;
import com.communitas.store.app.repository.VentaLibroRepository;
import com.communitas.store.app.repository.VentaOrdenRepository;
import com.communitas.store.app.response.UsuarioResponse;
import com.communitas.store.app.response.VentaLibroResponse;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/venta-libro")
public class VentaLibroController {


    private final ModelMapper modelMapper=new ModelMapper();
    @Autowired
    private VentaOrdenRepository ventaOrdenRepository;
    @Autowired
    private VentaLibroRepository ventaLibroRepository;


    private VentaLibroResponse mapVentaLibroResponse(VentaLibro ventaLibro) {
        return modelMapper.map(ventaLibro, VentaLibroResponse.class);
    }

    @GetMapping("/list")
    public List<VentaLibroResponse> listVentaLibros(){
        List<VentaLibro> ventaLibrosList= ventaLibroRepository.findAll();
        List<VentaLibroResponse> ventasLibrosResponse = ventaLibrosList.stream()
                .map(this::mapVentaLibroResponse)
                .collect(Collectors.toList());

        return ventasLibrosResponse;
    }


}
