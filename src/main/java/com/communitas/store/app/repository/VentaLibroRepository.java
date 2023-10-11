package com.communitas.store.app.repository;

import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaLibroRepository extends JpaRepository<VentaLibro, Integer> {

    @Query(value ="SELECT vt FROM VentaLibro vt WHERE vt.ventaOrden.idVentaOrden=?1",nativeQuery = true)
    Iterable<VentaLibro> miDetalleVenta(int idVentaOrden);

}

