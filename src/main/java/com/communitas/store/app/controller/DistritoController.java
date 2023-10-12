package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.DistritoDTO;
import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.repository.DistritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/distrito")
public class DistritoController {
    @Autowired
    private DistritoRepository distritoRepository;

    @GetMapping("/list")
    List<Distrito> listDistrito(){
        List<Distrito> listaDistrito= distritoRepository.findAll();
        return listaDistrito;
    }

}
