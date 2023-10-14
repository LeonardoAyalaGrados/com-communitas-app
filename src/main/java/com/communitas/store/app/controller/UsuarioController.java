package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.UsuarioDTO;
import com.communitas.store.app.controller.dto.UsuarioHomeDTO;
import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.repository.DistritoRepository;
import com.communitas.store.app.repository.UsuarioRepository;
import com.communitas.store.app.response.UsuarioResponse;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    // Función para mapear Usuario a UsuarioResponse
    private UsuarioResponse mapUsuarioToUsuarioResponse(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    // Endpoint para listar UsuarioResponse paginados
    @GetMapping
    public Page<UsuarioResponse> pageable(@PageableDefault(sort = "idUsuario", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        Page<Usuario> usuariosPaginados = usuarioRepository.findAll(pageable);

        // Mapea los usuarios a UsuarioResponse
        Page<UsuarioResponse> usuariosResponsePaginados = usuariosPaginados.map(this::mapUsuarioToUsuarioResponse);

        return usuariosResponsePaginados;
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

    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id){
        Optional<Usuario> usuarioEncontrado=Optional.of(usuarioRepository.findById(id).get());
        Optional<Distrito> distritoEncontrado=Optional.of(distritoRepository.findById(usuarioEncontrado.get().getDistrito().getIdDistrito()).orElseThrow());
        if (usuarioEncontrado.isEmpty()){
            throw new EntityNotFoundException();
        }

        /*
        Usuario usuario = usuarioEncontrado.get();

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setIdUsuario(usuario.getIdUsuario());
        usuarioResponse.setNombre(usuario.getNombre());
        usuarioResponse.setApellido(usuario.getApellido());
        usuarioResponse.setFullName(usuario.getFullName());
        usuarioResponse.setCelular(usuario.getCelular());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setContraseña(usuario.getContraseña());
        usuarioResponse.setRol(usuario.getRol());
        usuarioResponse.setCreadoEn(usuario.getCreadoEn());
        usuarioResponse.setActualizadoEn(usuario.getActualizadoEn());
        usuarioResponse.setDireccion(usuario.getDireccion());
        usuarioResponse.setDistrito(distritoEncontrado.get().getIdDistrito());
        usuarioResponse.setOrdenes(usuario.getOrdenes());
*/
        Usuario usuario = usuarioEncontrado.get();
        UsuarioResponse usuarioResponse = modelMapper.map(usuario, UsuarioResponse.class);
        //usuarioResponse.setDistrito(distritoEncontrado.get().getNombre());

        return new ResponseEntity<UsuarioResponse>(usuarioResponse, HttpStatus.OK);
    }

    @PutMapping("/updateuser/{id}")
    public Usuario updateUser(@PathVariable("id") Integer id ,@Validated @RequestBody UsuarioDTO usuarioDTO){
        Optional<Distrito> distrito=Optional.of(distritoRepository.findById(usuarioDTO.getDistrito().getIdDistrito())
                .orElseThrow(EntityNotFoundException::new));
        Optional<Usuario> usuarioEncontrado=usuarioRepository.findById(id);
        if (!usuarioEncontrado.isPresent()){
            throw new EntityNotFoundException();
        }
        modelMapper.map(usuarioDTO,usuarioEncontrado.get());
        usuarioEncontrado.get().setFullName(usuarioDTO.getNombre()+" "+usuarioDTO.getApellido());
        usuarioEncontrado.get().setDistrito(distrito.get());

        return  usuarioRepository.save(usuarioEncontrado.get());
    }

}
