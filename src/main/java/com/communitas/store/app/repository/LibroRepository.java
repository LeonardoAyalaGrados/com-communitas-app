package com.communitas.store.app.repository;

import com.communitas.store.app.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %?1%")
    List<Libro> findbyTitle(String titulo);
}
