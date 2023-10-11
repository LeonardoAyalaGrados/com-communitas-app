package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.UsuarioDTO;
import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.repository.DistritoRepository;
import com.communitas.store.app.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    private final ModelMapper modelMapper=new ModelMapper();
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DistritoRepository distritoRepository;
/*
    @PostMapping("/save")
    public Usuario saveUser(@Validated @RequestBody Usuario usuario){
        Optional<Distrito> distritoOptional=distritoRepository.findById(usuario.getDistrito().getIdDistrito());
        usuario.setDistrito(distritoOptional.get());
        Usuario usuarioGuardado=usuarioRepository.save(usuario);
        return usuarioGuardado;
    }
    */

    @GetMapping("/list")
    public List<Usuario> litUsuario(){
        return usuarioRepository.findAll();
    }

    @GetMapping
    public Page<Usuario> pageable(@PageableDefault(sort = "fullName",direction = Sort.Direction.ASC, size = 5) Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public Usuario saveUser(@Validated @RequestBody UsuarioDTO usuarioDTO){
        Optional<Usuario> usuarioEncontrado=usuarioRepository.findByEmail(usuarioDTO.getEmail().toLowerCase());
        if (usuarioEncontrado.isPresent()){
            throw new DataIntegrityViolationException("");
        }
        Optional<Distrito> distritoOptional=distritoRepository.findById(usuarioDTO.getDistrito().getIdDistrito());
        Usuario nuevoUsuario=modelMapper.map(usuarioDTO,Usuario.class);
        nuevoUsuario.setDistrito(distritoOptional.get());
        nuevoUsuario.setFullName(usuarioDTO.getNombre()+" "+usuarioDTO.getApellido());
        return usuarioRepository.save(nuevoUsuario);
    }
    @GetMapping("/findbyemail")
    public Usuario findByEmail(@RequestParam String email){
        Optional<Usuario>usuarioEncontrado=usuarioRepository.findByEmail(email);
        if (!usuarioEncontrado.isPresent()){
           throw  new EntityNotFoundException();
        }
        /*
        usuarioEncontrado.get().getDistrito();
        Integer idDistritousuario=usuarioEncontrado.get().getDistrito().getIdDistrito();
        System.out.println("ID USUARIO DISTRITO = "+idDistritousuario);
        */

        return usuarioEncontrado.get();
    }

}
