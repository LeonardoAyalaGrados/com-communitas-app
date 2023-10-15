package com.communitas.store.app.controller;

import com.communitas.store.app.BCryptPassword.BCryptPassword;
import com.communitas.store.app.controller.dto.UsuarioHomeDTO;
import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.repository.DistritoRepository;
import com.communitas.store.app.repository.LibroRepository;
import com.communitas.store.app.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/home")
public class HomeController {

    private ModelMapper modelMapper=new ModelMapper();
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private DistritoRepository distritoRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Usuario> loginAndroid(@RequestParam String email, String contraseña){
        Optional<Usuario> credentials=usuarioRepository.findByEmailAndPassword(email,contraseña);
        if (credentials.isEmpty()){
            throw new RuntimeException("credenciales incorrectas");
        }
        return new ResponseEntity<Usuario>(HttpStatus.OK);
    };

    @PostMapping("/save")
    public Usuario saveUserRegister(@Validated @RequestBody UsuarioHomeDTO usuarioHomeDTO){
        Optional<Usuario> usuarioEncontrado=usuarioRepository.findByEmail(usuarioHomeDTO.getEmail().toLowerCase());
        if (usuarioEncontrado.isPresent()){
            throw new DataIntegrityViolationException("");
        }
        Optional<Distrito> distritoOptional=distritoRepository.findById(usuarioHomeDTO.getDistrito().getIdDistrito());

        Usuario nuevoUsuario=modelMapper.map(usuarioHomeDTO,Usuario.class);
        String contraseñaEncriptada = passwordEncoder.encode(usuarioHomeDTO.getContraseña());
        nuevoUsuario.setContraseña(contraseñaEncriptada);
        System.out.println("contraseña ->"+contraseñaEncriptada);


        nuevoUsuario.setDistrito(distritoOptional.get());
        nuevoUsuario.setRol(Usuario.Rol.USUARIO);
        nuevoUsuario.setFullName(usuarioHomeDTO.getNombre()+" "+usuarioHomeDTO.getApellido());
        return usuarioRepository.save(nuevoUsuario);
    }

    @GetMapping("/findbytitle")
    public List<Libro> findByTitle(@RequestParam(name = "titulo") String titulo){
        return libroRepository.findbyTitle(titulo);
    }

    @PutMapping("/updateuser/{id}")
    public Usuario updateUser(@PathVariable("id") Integer id ,@Validated @RequestBody UsuarioHomeDTO usuarioHomeDTO){
        Optional<Distrito> distrito=Optional.of(distritoRepository.findById(usuarioHomeDTO.getDistrito().getIdDistrito())
                                                                                .orElseThrow(EntityNotFoundException::new));
        Optional<Usuario> usuarioEncontrado=usuarioRepository.findById(id);
        if (!usuarioEncontrado.isPresent()){
            throw new EntityNotFoundException();
        }
        modelMapper.map(usuarioHomeDTO,usuarioEncontrado.get());
        usuarioEncontrado.get().setFullName(usuarioHomeDTO.getNombre()+" "+usuarioHomeDTO.getApellido());
        usuarioEncontrado.get().setRol(Usuario.Rol.USUARIO);
        usuarioEncontrado.get().setDistrito(distrito.get());

        return  usuarioRepository.save(usuarioEncontrado.get());
    }
}
