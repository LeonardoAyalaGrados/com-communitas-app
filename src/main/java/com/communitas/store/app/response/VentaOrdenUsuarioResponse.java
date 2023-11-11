package com.communitas.store.app.response;


import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaOrden;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VentaOrdenUsuarioResponse {


    private Integer idVentaOrden;
    private Float total;
    private VentaOrden.Estado estado;
    private VentaOrden.TipoEntrega tipoEntrega;
    private LocalDateTime creadoEn;
    private UsuarioResponse usuario;
}
