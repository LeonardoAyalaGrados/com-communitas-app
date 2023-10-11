package com.communitas.store.app.repository;


import com.communitas.store.app.controller.dto.DistritoDTO;
import com.communitas.store.app.entity.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistritoRepository extends JpaRepository <Distrito,Integer> {
}
