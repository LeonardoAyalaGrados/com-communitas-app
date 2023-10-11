package com.communitas.store.app.repository;

import com.communitas.store.app.entity.Categoria;
import com.communitas.store.app.entity.VentaOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaOrdenRepository extends JpaRepository<VentaOrden, Integer> {

    @Query("SELECT o FROM VentaOrden o WHERE o.usuario.idUsuario=?1")
    List<VentaOrden> misPedidos(int idCliente);
}
