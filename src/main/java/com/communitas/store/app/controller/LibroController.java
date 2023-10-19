package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.LibroDTO;
import com.communitas.store.app.entity.Categoria;
import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.repository.CategoriaRepository;
import com.communitas.store.app.repository.LibroRepository;
import com.communitas.store.app.response.LibroResponse;
import com.communitas.store.app.response.UsuarioResponse;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Función para mapear Usuario a UsuarioResponse
    private LibroResponse mapUsuarioToUsuarioResponse(Libro libro) {
        return modelMapper.map(libro, LibroResponse.class);
    }

    @GetMapping
    public Page<LibroResponse> pageable(@PageableDefault(sort = "idLibro",direction = Sort.Direction.ASC, size = 5) Pageable pageable){
        Page<Libro> librosPaginados=libroRepository.findAll(pageable);

        Page<LibroResponse> librosResponsePaginados=librosPaginados.map(this::mapUsuarioToUsuarioResponse);
        return librosResponsePaginados;

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

    @GetMapping("/id/{id}")
    public ResponseEntity<LibroResponse> buscarPorId(@PathVariable Integer id){
        Optional<Libro> libroEncontrado=Optional.of(libroRepository.findById(id).get());
        Optional<Categoria> categoriaEncontrado=Optional.of(categoriaRepository.findById(libroEncontrado.get().getCategoria().getIdCategoria()).orElseThrow());
        if (libroEncontrado.isEmpty()){
            throw new EntityNotFoundException();
        }
        Libro libro = libroEncontrado.get();
        LibroResponse libroResponse = modelMapper.map(libro, LibroResponse.class);
        //usuarioResponse.setDistrito(distritoEncontrado.get().getNombre());

        return new ResponseEntity<LibroResponse>(libroResponse, HttpStatus.OK);
    }
}
