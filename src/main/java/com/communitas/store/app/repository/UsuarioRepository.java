package com.communitas.store.app.repository;

import com.communitas.store.app.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query(value ="SELECT u FROM Usuario u WHERE u.email=?1 AND u.contraseña=?2")
     Optional<Usuario> findByEmailAndPassword(String email, String contraseña);

    @Query(value ="SELECT u FROM Usuario u WHERE u.email=?1")
    Optional<Usuario> findByEmail(String email);

}
