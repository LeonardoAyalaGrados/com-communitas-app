package com.communitas.store.app.controller;


import com.communitas.store.app.entity.Categoria;
import com.communitas.store.app.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaRepository  categoriaRepository;

    @GetMapping("/list")
    public List<Categoria> listCategoria(){
        return categoriaRepository.findAll();
    }

}
