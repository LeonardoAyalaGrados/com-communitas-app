package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.LibroDTO;
import com.communitas.store.app.entity.Categoria;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.repository.CategoriaRepository;
import com.communitas.store.app.repository.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/libro")
public class LibroController {

    private ModelMapper modelMapper=new ModelMapper();
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/list")
    public List<Libro> listLibro(){
        return libroRepository.findAll();
    }


    @GetMapping
    public Page<Libro> pageable(@PageableDefault(sort = "idLibro",direction = Sort.Direction.ASC, size = 5) Pageable pageable){
        return libroRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public Libro saveLibro(@Validated @RequestBody LibroDTO libroDTO){
        Optional<Categoria> categoriaOptional=categoriaRepository.findById(libroDTO.getCategoria().getIdCategoria());
        if (categoriaOptional.isEmpty()){
            throw new EntityNotFoundException("");
        }

        Libro nuevoLibro=modelMapper.map(libroDTO, Libro.class);
        nuevoLibro.setCategoria(categoriaOptional.get());
        return libroRepository.save(nuevoLibro);


    }
}
